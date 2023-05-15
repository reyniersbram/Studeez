package be.ugent.sel.studeez.screens.session.sessionScreens.composables

import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.session.SessionActions

@Composable
fun EndlessTimerSessionScreenComposable(
    open: (String) -> Unit,
    sessionActions: SessionActions,
) {
    SessionScreen(
        open = open,
        sessionActions = sessionActions
    ) {
        motivationString()
    }
}

@Composable
private fun motivationString(): String {
    return resources().getString(R.string.state_focus)
}