package be.ugent.sel.studeez.screens.session.sessionScreens

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText


class CustomSessionScreen(
    private val functionalTimer: FunctionalCustomTimer,
    private var mediaplayer: MediaPlayer?
): AbstractSessionScreen() {

    @Composable
    override fun motivationString(): String {
        if (functionalTimer.hasEnded()) {
            return resources().getString(AppText.state_done)
        }
        return resources().getString(AppText.state_focus)
    }

    override fun callMediaPlayer() {
        if (functionalTimer.hasEnded()) {
            mediaplayer?.let { it: MediaPlayer ->
                it.setOnCompletionListener {
                    it.release()
                    mediaplayer = null
                }
                it.start()
            }
        }
    }

}