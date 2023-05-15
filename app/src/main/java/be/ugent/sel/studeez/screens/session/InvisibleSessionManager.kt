package be.ugent.sel.studeez.screens.session

import android.media.MediaPlayer
import kotlinx.coroutines.delay
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
object InvisibleSessionManager {
    private var viewModel: SessionViewModel? = null
    private lateinit var mediaPlayer: MediaPlayer

    fun setParameters(viewModel: SessionViewModel, mediaPlayer: MediaPlayer) {
        this.mediaPlayer = mediaPlayer
        this.viewModel = viewModel
    }

    suspend fun updateTimer() {
        viewModel?.let {
            while (!it.getTimer().hasEnded()) {
                delay(1.seconds)
                it.getTimer().tick()
                if (it.getTimer().hasCurrentCountdownEnded()) {
                    mediaPlayer.start()
                }
            }
        }
    }
}