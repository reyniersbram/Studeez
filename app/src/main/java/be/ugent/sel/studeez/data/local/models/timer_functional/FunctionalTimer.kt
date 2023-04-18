package be.ugent.sel.studeez.data.local.models.timer_functional

abstract class FunctionalTimer(initialValue: Int) {
    val time: Time = Time(initialValue)
    var view: StudyState = StudyState.FOCUS

    fun getHoursMinutesSeconds(): HoursMinutesSeconds {
        return time.getAsHMS()
    }

    abstract fun tick()

    abstract fun hasEnded(): Boolean

    fun hasCurrentCountdownEnded(): Boolean {
        return time.getTime() == 0
    }

    enum class StudyState {
        FOCUS, DONE, BREAK, FOCUS_REMAINING
    }

}