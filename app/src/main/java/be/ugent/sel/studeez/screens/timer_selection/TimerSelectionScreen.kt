package be.ugent.sel.studeez.screens.timer_selection

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.composable.TimePickerButton
import be.ugent.sel.studeez.common.composable.TimerEntry
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.data.local.models.timer_functional.Time
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TimerSelectionActions(
    val getAllTimers: () -> Flow<List<TimerInfo>>,
    val startSession: (TimerInfo) -> Unit,
    val customTimeStudyTime: Int
)

fun getTimerSelectionActions(
    viewModel: TimerSelectionViewModel,
    open: (String) -> Unit,
): TimerSelectionActions {
    return TimerSelectionActions(
        getAllTimers = viewModel::getAllTimers,
        startSession = { viewModel.startSession(open, it) },
        customTimeStudyTime = viewModel.customTimerStudyTime.value
    )
}

@Composable
fun TimerSelectionRoute(
    open: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: TimerSelectionViewModel,
) {
    TimerSelectionScreen(
        timerSelectionActions = getTimerSelectionActions(viewModel, open),
        popUp = popUp
    )
}

@Composable
fun TimerSelectionScreen(
    timerSelectionActions: TimerSelectionActions,
    popUp: () -> Unit
) {
    val timers = timerSelectionActions.getAllTimers().collectAsState(initial = emptyList())
    SecondaryScreenTemplate(
        title = resources().getString(R.string.timers),
        popUp = popUp
    ) {
        LazyColumn {
            // Custom timer with duration selection button
            item {
                CustomTimerEntry(timerSelectionActions)
            }

            // All timers
            items(timers.value) { timerInfo ->
                TimerEntry(
                    timerInfo = timerInfo,
                    leftButton = {
                        StealthButton(
                            text = R.string.start,
                            onClick = { timerSelectionActions.startSession(timerInfo) }
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun CustomTimerEntry(
    timerSelectionActions: TimerSelectionActions
) {
    val timerInfo = CustomTimerInfo(
        name = resources().getString(R.string.custom_name),
        description = resources().getString(R.string.custom_description),
        studyTime = timerSelectionActions.customTimeStudyTime
    )
    val hms: HoursMinutesSeconds = Time(timerInfo.studyTime).getAsHMS()

    TimerEntry(
        timerInfo = timerInfo,
        leftButton = {
            StealthButton(
                text = R.string.start,
                onClick = { timerSelectionActions.startSession(timerInfo) }
            )
        },
        rightButton = {
            TimePickerButton(
                initialSeconds = hms.getTotalSeconds(),
                modifier = Modifier.padding(horizontal = 5.dp)
            ) { chosenTime ->
                timerInfo.studyTime = chosenTime
            }
        }
    )
}

@Preview
@Composable
fun TimerSelectionPreview() {
    TimerSelectionScreen(
        timerSelectionActions = TimerSelectionActions({ flowOf() }, {}, 0),
        popUp = {}
    )
}