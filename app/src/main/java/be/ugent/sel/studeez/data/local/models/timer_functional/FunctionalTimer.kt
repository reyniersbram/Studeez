package be.ugent.sel.studeez.data.local.models.timer_functional

abstract class FunctionalTimer(initialValue: Int) {
    val time: Time = Time(initialValue)
    var view: String = FOCUS

    fun getHoursMinutesSeconds(): HoursMinutesSeconds {
        return time.getAsHMS()
    }

    abstract fun tick()

    abstract fun hasEnded(): Boolean

    companion object {
        const val FOCUS: String = "Focus"
        const val DONE: String = "Done!"
        const val BREAK: String = "Take a break!"
        val FOCUS_REMAINING: (Int) -> String = { n -> "Focus! ($n breaks remaining)" }
    }

}