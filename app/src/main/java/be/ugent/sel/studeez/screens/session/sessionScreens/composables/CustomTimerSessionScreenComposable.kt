package be.ugent.sel.studeez.screens.session.sessionScreens.composables

import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.session.SessionActions

@Composable
fun CustomTimerSessionScreenComposable(
    open: (String) -> Unit,
    sessionActions: SessionActions,
    customTimer: FunctionalCustomTimer
) {
    SessionScreen(
        open = open,
        sessionActions = sessionActions
    ) {
        motivationString(customTimer = customTimer)
    }
}

@Composable
private fun motivationString(customTimer: FunctionalCustomTimer): String {
    if (customTimer.hasEnded()) {
        return resources().getString(R.string.state_done)
    }
    return resources().getString(R.string.state_focus)
}