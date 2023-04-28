package be.ugent.sel.studeez.screens.session.sessionScreens

import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText


class EndlessSessionScreen : AbstractSessionScreen() {

    @Composable
    override fun motivationString(): String {
        return resources().getString(AppText.state_focus)
    }
}