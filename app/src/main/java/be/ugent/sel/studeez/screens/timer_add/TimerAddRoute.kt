package be.ugent.sel.studeez.screens.timer_add

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.screens.timer_edit.GetTimerEditScreen
import be.ugent.sel.studeez.screens.timer_edit.TimerEditViewModel

data class TimerEditActions(
    val getTimerInfo: () -> TimerInfo,
    val saveTimer: (TimerInfo) -> Unit
)

fun getTimerAddActions(
    viewModel: TimerEditViewModel,
    goBack: () -> Unit
): TimerEditActions {
    return TimerEditActions(
        getTimerInfo = viewModel::getTimerInfo,
        saveTimer = { timerInfo -> viewModel.saveTimer(timerInfo, goBack) }
    )
}

@Composable
fun TimerAddRoute(
    open: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: TimerEditViewModel,
) {

    val timerEditActions = getTimerAddActions(viewModel, goBack = popUp)

    SecondaryScreenTemplate(title = stringResource(id = R.string.edit_timer), popUp = popUp) {

        val timerEditScreen = timerEditActions.getTimerInfo().accept(GetTimerEditScreen())
        timerEditScreen { timerEditActions.saveTimer(it) }
    }
}