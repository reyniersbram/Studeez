package be.ugent.sel.studeez.data.local.models.timer_functional

import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.screens.session.sessionScreens.AbstractSessionScreen
import com.google.firebase.Timestamp

abstract class FunctionalTimer(initialValue: Int) {
    val time: Time = Time(initialValue)
    var totalStudyTime: Int = 0

    fun getHoursMinutesSeconds(): HoursMinutesSeconds {
        return time.getAsHMS()
    }

    abstract fun tick()

    abstract fun hasEnded(): Boolean

    abstract fun hasCurrentCountdownEnded(): Boolean

    fun getSessionReport(): SessionReport {
        return SessionReport(
            id = "",
            studyTime = totalStudyTime,
            endTime = Timestamp.now()
        )
    }

    abstract fun <T> accept(visitor: FunctionalTimerVisitor<T>): T
}