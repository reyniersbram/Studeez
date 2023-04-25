package be.ugent.sel.studeez.screens.session.SessionScreens

import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText


class EndlessSessionScreen(
    functionalTimer: FunctionalEndlessTimer
    ): SessionScreen(functionalTimer) {

    @Composable
    override fun motivationString(): String {
        return resources().getString(AppText.state_focus)
    }
}