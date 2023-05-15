package be.ugent.sel.studeez.screens.timer_form.timer_type_select

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.data.local.models.timer_info.*
import be.ugent.sel.studeez.R.string as AppText
import be.ugent.sel.studeez.data.local.models.timer_info.TimerType.CUSTOM
import be.ugent.sel.studeez.data.local.models.timer_info.TimerType.BREAK
import be.ugent.sel.studeez.data.local.models.timer_info.TimerType.ENDLESS

val defaultTimerInfo: Map<TimerType, TimerInfo> = mapOf(
    CUSTOM to CustomTimerInfo("", "", 0),
    BREAK to PomodoroTimerInfo("", "", 0, 0, 1),
    ENDLESS to EndlessTimerInfo("", ""),
)


@Composable
fun TimerTypeSelectScreen(
    open: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: TimerTypeSelectViewModel = hiltViewModel()
) {

    SecondaryScreenTemplate(title = stringResource(id = AppText.timer_type_select), popUp = popUp) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            TimerType.values().forEach { timerType ->
                val default: TimerInfo = defaultTimerInfo.getValue(timerType)
                Button(
                    onClick = { viewModel.onTimerTypeChosen(default, open) },
                    modifier = Modifier.fillMaxWidth().padding(5.dp)
                ) {
                    Text(text = timerType.name)
                }
            }
        }
    }
}