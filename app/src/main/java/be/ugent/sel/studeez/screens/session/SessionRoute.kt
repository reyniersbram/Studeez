package be.ugent.sel.studeez.screens.session

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.screens.session.sessionScreens.AbstractSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.GetSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.composables.BreakSessionScreenComposable
import be.ugent.sel.studeez.screens.session.sessionScreens.composables.GetSessionScreenComposable

data class SessionActions(
    val getTimer: () -> FunctionalTimer,
    val getTask: () -> String,
    val startMediaPlayer: () -> Unit,
    val releaseMediaPlayer: () -> Unit,
    val endSession: () -> Unit
)

private fun getSessionActions(
    viewModel: SessionViewModel,
    openAndPopUp: (String, String) -> Unit,
    mediaplayer: MediaPlayer,
): SessionActions {
    return SessionActions(
        getTimer = viewModel::getTimer,
        getTask = viewModel::getTask,
        endSession = { viewModel.endSession(openAndPopUp) },
        startMediaPlayer = mediaplayer::start,
        releaseMediaPlayer = mediaplayer::release,
    )
}

@Composable
fun SessionRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SessionViewModel,
) {
    val context = LocalContext.current
    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val mediaplayer = MediaPlayer.create(context, uri)
    mediaplayer.isLooping = false

    InvisibleSessionManager.setParameters(
        viewModel = viewModel,
        mediaplayer = mediaplayer
    )

    val sessionActions = getSessionActions(viewModel, openAndPopUp, mediaplayer)
    val sessionScreen = viewModel.getTimer().accept(GetSessionScreenComposable(mediaplayer, open, sessionActions))

    sessionScreen()
}
