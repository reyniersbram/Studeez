package be.ugent.sel.studeez.data.local.models.timer_functional

import android.media.MediaPlayer

abstract class FunctionalTimer(initialValue: Int) {
    val time: Time = Time(initialValue)
    var mediaPlayer: MediaPlayer? = null

    fun getHoursMinutesSeconds(): HoursMinutesSeconds {
        return time.getAsHMS()
    }

    abstract fun tick()

    abstract fun hasEnded(): Boolean

    abstract fun hasCurrentCountdownEnded(): Boolean

    abstract fun <T> accept(visitor: FunctionalTimerVisitor<T>): T
}