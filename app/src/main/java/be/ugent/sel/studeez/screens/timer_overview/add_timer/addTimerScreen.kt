package be.ugent.sel.studeez.screens.timer_overview.add_timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.composable.navbar.BasicTimePicker
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme

data class AddTimerActions(
    val open: (String) -> Unit,
    val goBack: () -> Unit,
    val onStudyTimeHoursChange: (Int) -> Unit,
    val onStudyTimeMinutesChange: (Int) -> Unit,
    val onBreakTimeHourChange: (Int) -> Unit,
    val onBreakTimeMinutesChange: (Int) -> Unit,
    val onRepeatsChange: (Int) -> Unit,
    val onWithBreaksChange: () -> Unit,
    val addTimer: () -> Unit,
    val onNameChange: (String) -> Unit,
    val onDescriptionChange: (String) -> Unit,
)

fun getAddTimerActions(
    open: (String) -> Unit,
    goBack: () -> Unit,
    viewModel: AddTimerViewModel,
): AddTimerActions {
    return AddTimerActions(
        open = open,
        goBack = goBack,
        onWithBreaksChange = viewModel::onWithBreaksChange,
        onStudyTimeHoursChange = viewModel::onStudyTimeHoursChange,
        onStudyTimeMinutesChange = viewModel::onStudyTimeMinutesChange,
        onBreakTimeHourChange = viewModel::onBreakTimeHourChange,
        onBreakTimeMinutesChange = viewModel::onBreakTimeMinutesChange,
        onRepeatsChange = viewModel::onRepeatsChange,
        addTimer = viewModel::addTimer,
        onNameChange = viewModel::onNameChange,
        onDescriptionChange = viewModel::onDescriptionChange
    )
}

@Composable
fun AddTimerRoute(
    open: (String) -> Unit,
    goBack: () -> Unit,
    viewModel: AddTimerViewModel,
) {
    val uiState by viewModel.uiState

    AddTimerScreen(
        addTimerActions = getAddTimerActions(
            open = open,
            goBack = goBack,
            viewModel = viewModel,
        ),
        uiState = uiState
    )
}

@Composable
fun AddTimerScreen(
    addTimerActions: AddTimerActions,
    uiState: AddTimerUiState,
) {
    val mStudyTimePicker = BasicTimePicker(
        onHoursChange = addTimerActions.onStudyTimeHoursChange,
        onMinutesChange = addTimerActions.onStudyTimeMinutesChange,
        Hours = uiState.studyTimeHours,
        Minutes = uiState.studyTimeMinutes
    )

    val mBreakTimePicker = BasicTimePicker(
        onHoursChange = addTimerActions.onBreakTimeHourChange,
        onMinutesChange = addTimerActions.onBreakTimeMinutesChange,
        Hours = uiState.breakTimeHours,
        Minutes = uiState.breakTimeMinutes
    )

    SecondaryScreenTemplate(
        title = resources().getString(R.string.add_timer),
        popUp = addTimerActions.goBack
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.addTimer_question),
                        textAlign = TextAlign.Center
                    )
                }
            }

            item {
                Text(
                    text = uiState.studyTimeHours.toString() + stringResource(R.string.addTimer_studytime_1) + uiState.studyTimeMinutes + stringResource(
                                            R.string.addTimer_studytime_2)
                )
            }

            item {
                Button(
                    onClick = {
                        mStudyTimePicker.show()
                    },
                ) {
                    Text(
                        text = stringResource(R.string.addTimer_timepicker),
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.addTimer_break_question),
                    )
                    Checkbox(
                        checked = uiState.withBreaks,
                        onCheckedChange = { addTimerActions.onWithBreaksChange() }
                    )
                }
            }

            if (uiState.withBreaks) {
                item {
                    Text(
                        text = if (uiState.repeats == 1) uiState.repeats.toString() + stringResource(
                                                    R.string.addTimer_break_1)
                               else uiState.repeats.toString() + stringResource(
                                                    R.string.addTimer_break_s)
                    )
                    TextField(
                        value = uiState.repeats.toString(),
                        onValueChange = { it: String ->
                            it.toIntOrNull()?.let { it1 ->
                                addTimerActions.onRepeatsChange(
                                    kotlin.math.abs(it1)
                                )
                            }
                        }
                    )
                }

                item {
                    Text(
                        text = uiState.breakTimeHours.toString() + stringResource(R.string.breakTime_1) + uiState.breakTimeMinutes + stringResource(
                                                    R.string.breakTime_2)
                    )
                }

                item {
                    Button(
                        onClick = {
                            mBreakTimePicker.show()
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.addTimer_timepicker),
                        )
                    }
                }
            }

            item {
                Text(
                    text = stringResource(R.string.addTimer_name)
                )
            }

            item {
                TextField(
                    value = uiState.name,
                    onValueChange = { addTimerActions.onNameChange(it) }
                )
            }

            item {
                if (uiState.name == "") {
                    Text(
                        text = stringResource(R.string.addTimer_name_error),
                        color = Color.Red
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.addTimer_description)
                )
            }

            item {
                TextField(
                    value = uiState.description,
                    onValueChange = { addTimerActions.onDescriptionChange(it) }
                )
            }

            item {
                if (uiState.description == "") {
                    Text(
                        text = stringResource(R.string.addTimer_description_error),
                        color = Color.Red
                    )
                }
            }
            
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    BasicButton(
                        text = R.string.add_timer,
                        modifier = Modifier,
                        onClick = {
                            if (uiState.description != "" && uiState.name != "") {
                                addTimerActions.addTimer()
                                addTimerActions.open(StudeezDestinations.TIMER_OVERVIEW_SCREEN)
                            }
                         }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AddTimerScreenPreview() { StudeezTheme {
    AddTimerScreen(
        addTimerActions = AddTimerActions({}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}),
        uiState = AddTimerUiState()
        )
    }
}
