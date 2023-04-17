package be.ugent.sel.studeez.data.local.models.timer_functional

abstract class FunctionalTimer(initialValue: Int) {
    protected val time: Time = Time(initialValue)
    protected var view: String = "Focus"

    fun getHoursMinutesSeconds(): HoursMinutesSeconds {
        return time.getAsHMS()
    }

    fun getViewString(): String {
        return view
    }

    abstract fun tick()

    abstract fun hasEnded(): Boolean

}