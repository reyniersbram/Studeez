package be.ugent.sel.studeez.screens.session

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.screens.session.sessionScreens.BreakSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.CustomSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.EndlessSessionScreen

data class SessionActions(
    val getTimer: () -> FunctionalTimer,
    val getTask: () -> String,
    val prepareMediaPlayer: () -> Unit,
    val releaseMediaPlayer: () -> Unit,
)

private fun getSessionActions(
    viewModel: SessionViewModel,
    mediaplayer: MediaPlayer,
): SessionActions {
    return SessionActions(
        getTimer = viewModel::getTimer,
        getTask = viewModel::getTask,
        prepareMediaPlayer = mediaplayer::prepareAsync,
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

    val sessionScreen = when (val timer = viewModel.getTimer()) {
        is FunctionalCustomTimer -> CustomSessionScreen(timer)
        is FunctionalPomodoroTimer -> BreakSessionScreen(timer)
        is FunctionalEndlessTimer -> EndlessSessionScreen()
        else -> throw java.lang.IllegalArgumentException("Unknown Timer")
    }

    sessionScreen(
        open = open,
        sessionActions = getSessionActions(viewModel, mediaplayer)
    )
}
