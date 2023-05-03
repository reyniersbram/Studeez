package be.ugent.sel.studeez.screens.timer_add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.data.local.models.timer_info.*
import be.ugent.sel.studeez.data.local.models.timer_info.TimerType.CUSTOM
import be.ugent.sel.studeez.data.local.models.timer_info.TimerType.BREAK
import be.ugent.sel.studeez.data.local.models.timer_info.TimerType.ENDLESS

val defaultTimerInfo: Map<TimerType, TimerInfo> = mapOf(
    CUSTOM to CustomTimerInfo("", "", 0),
    BREAK to PomodoroTimerInfo("", "", 0, 0, 0),
    ENDLESS to EndlessTimerInfo("", ""),
)


@Composable
fun TimerTypeSelectScreen(
    open: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: TimerTypeSelectViewModel = hiltViewModel()
) {

    SecondaryScreenTemplate(title = "Edit Timer", popUp = popUp) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TimerType.values().forEach { timerType ->
                Button(onClick = { viewModel.onTimerTypeChosen(defaultTimerInfo[timerType]!!, open) }) {
                    Text(text = timerType.name)
                }
            }
        }
    }
}