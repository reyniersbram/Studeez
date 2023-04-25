package be.ugent.sel.studeez.screens.session

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.screens.session.sessionScreens.AbstractSessionScreen

data class SessionActions(
    val getTimer: () -> FunctionalTimer,
    val getTask: () -> String,
    val prepareMediaPlayer: () -> Unit,
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
        prepareMediaPlayer = mediaplayer::prepareAsync,
        releaseMediaPlayer = mediaplayer::release
    )
}

@Composable
fun SessionRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SessionViewModel,
) {
    val sessionScreen: AbstractSessionScreen = viewModel.getTimer().getView()
    val context = LocalContext.current
    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val mediaplayer = MediaPlayer()
    mediaplayer.setDataSource(context, uri)
    mediaplayer.setOnCompletionListener {
        mediaplayer.stop()
        //if (timerEnd) {
//            mediaplayer.release()
        //}
    }
    mediaplayer.setOnPreparedListener {
//        mediaplayer.start()
    }
    sessionScreen.SessionScreen(
        open = open,
        sessionActions = getSessionActions(viewModel, openAndPopUp, mediaplayer),
    )
}
