package be.ugent.sel.studeez.screens.session

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import kotlinx.coroutines.delay
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
object InvisibleSessionManager {
    private var viewModel: SessionViewModel? = null
    private lateinit var mediaPlayer: MediaPlayer

    fun setParameters(viewModel: SessionViewModel, context: Context) {
        val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        this.mediaPlayer = MediaPlayer.create(context, uri)
        this.mediaPlayer.isLooping = false
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