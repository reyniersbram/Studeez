package be.ugent.sel.studeez.screens.timer_edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.R.string as AppText

data class TimerEditActions(
    val getTimerInfo: () -> TimerInfo,
    val editTimer: (TimerInfo, () -> Unit) -> Unit
)

fun getTimerEditActions(
    viewModel: TimerEditViewModel,
    open: (String) -> Unit
): TimerEditActions {
    return TimerEditActions(
        getTimerInfo = viewModel::getTimerInfo,
        editTimer = viewModel::editTimer
    )
}

@Composable
fun TimerEditRoute(
    open: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: TimerEditViewModel,
) {

    val timerEditActions = getTimerEditActions(viewModel, open)

    SecondaryScreenTemplate(title = stringResource(id = AppText.edit_timer), popUp = popUp) {

        val timerEditScreen = timerEditActions.getTimerInfo().accept(GetTimerEditScreen())
        timerEditScreen { timerInfo ->
            timerEditActions.editTimer(timerInfo, popUp)
        }
    }
}























