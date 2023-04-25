package be.ugent.sel.studeez.screens.session.sessionScreens

import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

class BreakSessionScreen(
    private val funPomoDoroTimer: FunctionalPomodoroTimer
): AbstractSessionScreen() {

    @Composable
    override fun motivationString(): String {
        if (funPomoDoroTimer.isInBreak) {
            return resources().getString(AppText.state_take_a_break)
        }

        if (funPomoDoroTimer.hasEnded()) {
            return resources().getString(AppText.state_done)
        }

        return resources().getQuantityString(
            R.plurals.state_focus_remaining,
            funPomoDoroTimer.breaksRemaining,
            funPomoDoroTimer.breaksRemaining
        )
    }

}