package be.ugent.sel.studeez.data.local.models.timer_functional

abstract class FunctionalTimer(initialValue: Int) {
    val time: Time = Time(initialValue)
    var view: StudyState = StudyState.FOCUS

    fun getHoursMinutesSeconds(): HoursMinutesSeconds {
        return time.getAsHMS()
    }

    abstract fun tick()

    abstract fun hasEnded(): Boolean

    enum class StudyState {
        FOCUS, DONE, BREAK, FOCUS_REMAINING
    }

}