package be.ugent.sel.studeez.screens.timer_form.timer_edit

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.screens.timer_form.GetTimerFormScreen
import be.ugent.sel.studeez.screens.timer_form.TimerFormViewModel
import java.util.Timer
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun TimerAddRoute(
    popUp: () -> Unit,
    viewModel: TimerFormViewModel
) {
    TimerFormScreen(popUp = popUp, getTimerInfo = viewModel::getTimerInfo, AppText.add_timer) {
        viewModel.saveTimer(it, goBack = popUp)
    }
}

@Composable
fun TimerEditRoute(
    popUp: () -> Unit,
    viewModel: TimerFormViewModel
) {
    TimerFormScreen(popUp = popUp, getTimerInfo = viewModel::getTimerInfo, AppText.edit_timer) {
        viewModel.editTimer(it, goBack = popUp)
    }
}

@Composable
fun TimerFormScreen(
    popUp: () -> Unit,
    getTimerInfo: () -> TimerInfo,
    @StringRes label: Int,
    onConfirmClick: (TimerInfo) -> Unit
) {
    val timerEditScreen = getTimerInfo().accept(GetTimerFormScreen())

    SecondaryScreenTemplate(title = stringResource(id = label), popUp = popUp) {
        timerEditScreen(onConfirmClick)
    }
}























