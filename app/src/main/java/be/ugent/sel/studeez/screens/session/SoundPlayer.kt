package be.ugent.sel.studeez.screens.session

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager

class SoundPlayer(private val context: Context) {

    var oldValue: Boolean = false
    var mediaPlayer: MediaPlayer = initPlayer()

    fun playOn(newValue: Boolean) {
        if (oldValue != newValue) {
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                mediaPlayer = initPlayer()
            }
            oldValue = newValue
        }
    }


    private fun initPlayer(): MediaPlayer {
        return  MediaPlayer.create(
            context,
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        )
    }
}