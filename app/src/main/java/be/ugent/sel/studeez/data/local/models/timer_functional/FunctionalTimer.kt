package be.ugent.sel.studeez.data.local.models.timer_functional

abstract class FunctionalTimer(initialValue: Int) {
    val time: Time = Time(initialValue)
    var view: String = "Focus"

    fun getHoursMinutesSeconds(): HoursMinutesSeconds {
        return time.getAsHMS()
    }

    abstract fun tick()

    abstract fun hasEnded(): Boolean

}