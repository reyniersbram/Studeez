package be.ugent.sel.studeez.screens.timer_selection

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.NotInternationalisedButton
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.composable.TimerEntry
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

data class TimerSelectionActions(
    val getAllTimers: () -> Flow<List<TimerInfo>>,
    val startSession: (TimerInfo) -> Unit,
    val pickDuration: (Context, CustomTimerInfo) -> Unit
)

fun getTimerSelectionActions(
    viewModel: TimerSelectionViewModel,
    open: (String) -> Unit,
): TimerSelectionActions {
    return TimerSelectionActions(
        getAllTimers = viewModel::getAllTimers,
        startSession = { viewModel.startSession(open, it) },
        pickDuration = { context, timerInfo ->
            val mCalendar = Calendar.getInstance()
            val mHour = mCalendar[Calendar.HOUR]
            val mMinute = mCalendar[Calendar.MINUTE]

            val mTimePickerDialog = TimePickerDialog(
                context,
                { _, hour : Int, minute: Int -> timerInfo.studyTime = hour * 60 * 60 + minute * 60 },
                mHour,
                mMinute,
                true
            )

            mTimePickerDialog.show()
        }
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
                val timerInfo = CustomTimerInfo(
                    name = resources().getString(R.string.custom_name),
                    description = resources().getString(R.string.custom_name),
                    studyTime = 0
                )
                val context = LocalContext.current

                TimerEntry(
                    timerInfo = timerInfo,
                    leftButton = {
                        StealthButton(
                            text = R.string.start,
                            onClick = { timerSelectionActions.startSession(timerInfo) }
                        )
                    },
                    rightButton = {
                        NotInternationalisedButton(
                            text = resources().getString(R.string.pick_time),
                            onClick = { timerSelectionActions.pickDuration(context, timerInfo) }
                        )
                    }
                )
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

@Preview
@Composable
fun TimerSelectionPreview() {
    TimerSelectionScreen(
        timerSelectionActions = TimerSelectionActions({ flowOf() }, {}, { _, _ -> {}}),
        popUp = {}
    )
}