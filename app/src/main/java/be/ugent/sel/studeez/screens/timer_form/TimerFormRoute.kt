package be.ugent.sel.studeez.screens.timer_form

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.data.local.models.timer_info.PomodoroTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
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
    val timerFormScreen = getTimerInfo().accept(GetTimerFormScreen())

    SecondaryScreenTemplate(title = stringResource(id = label), popUp = popUp) {
        timerFormScreen(onConfirmClick)
    }
}

@Preview
@Composable
fun AddTimerPreview() {
    TimerFormScreen(
        popUp = {  },
        getTimerInfo = { PomodoroTimerInfo("", "", 0, 0, 0) },
        label = AppText.add_timer,
        onConfirmClick = {}
    )
}
