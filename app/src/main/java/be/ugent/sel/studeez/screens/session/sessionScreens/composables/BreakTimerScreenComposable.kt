package be.ugent.sel.studeez.screens.session.sessionScreens.composables

import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.session.SessionActions

@Composable
fun BreakSessionScreenComposable(
    open: (String) -> Unit,
    sessionActions: SessionActions,
    pomodoroTimer: FunctionalPomodoroTimer
) {
    SessionScreen(
        open = open,
        sessionActions = sessionActions
    ) {
        motivationString(pomodoroTimer = pomodoroTimer)
    }
}

@Composable
private fun motivationString(pomodoroTimer: FunctionalPomodoroTimer): String {
    if (pomodoroTimer.isInBreak) {
        return resources().getString(R.string.state_take_a_break)
    }

    if (pomodoroTimer.hasEnded()) {
        return resources().getString(R.string.state_done)
    }

    return resources().getQuantityString(
        R.plurals.state_focus_remaining,
        pomodoroTimer.breaksRemaining,
        pomodoroTimer.breaksRemaining
    )
}