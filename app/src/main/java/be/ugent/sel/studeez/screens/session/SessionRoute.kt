package be.ugent.sel.studeez.screens.session

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.screens.session.sessionScreens.AbstractSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.GetSessionScreen

data class SessionActions(
    val getTimer: () -> FunctionalTimer,
    val getTask: () -> String,
    val startMediaPlayer: () -> Unit,
    val releaseMediaPlayer: () -> Unit,
)

private fun getSessionActions(
    viewModel: SessionViewModel,
    mediaplayer: MediaPlayer,
): SessionActions {
    return SessionActions(
        getTimer = viewModel::getTimer,
        getTask = viewModel::getTask,
        startMediaPlayer = mediaplayer::start,
        releaseMediaPlayer = mediaplayer::release,
    )
}

@Composable
fun SessionRoute(
    open: (String) -> Unit,
    viewModel: SessionViewModel,
) {
    val context = LocalContext.current
    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val mediaplayer = MediaPlayer.create(context, uri)
    mediaplayer.isLooping = false

    viewModel.getTimer().mediaPlayer = mediaplayer

    InvisibleSessionManager.setParameters(
        viewModel = viewModel
    )

    val sessionScreen: AbstractSessionScreen = viewModel.getTimer().accept(GetSessionScreen())

    sessionScreen(
        open = open,
        sessionActions = getSessionActions(viewModel, mediaplayer)
    )
}
