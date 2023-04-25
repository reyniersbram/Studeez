package be.ugent.sel.studeez.screens.session.SessionScreens

import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText


class CustomSessionScreen(
    private val functionalTimer: FunctionalCustomTimer
): SessionScreen(functionalTimer) {

    @Composable
    override fun motivationString(): String {
        if (functionalTimer.hasEnded()) {
            return resources().getString(AppText.state_done)
        }
        return resources().getString(AppText.state_focus)
    }

}