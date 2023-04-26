package be.ugent.sel.studeez.data.local.models.timer_functional

import be.ugent.sel.studeez.screens.session.sessionScreens.AbstractSessionScreen
abstract class FunctionalTimer(initialValue: Int) {
    val time: Time = Time(initialValue)

    fun getHoursMinutesSeconds(): HoursMinutesSeconds {
        return time.getAsHMS()
    }

    abstract fun tick()

    abstract fun hasEnded(): Boolean

    abstract fun hasCurrentCountdownEnded(): Boolean
}