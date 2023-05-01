package be.ugent.sel.studeez.data.local.models.timer_functional

data class HoursMinutesSeconds(val hours: Int, val minutes: Int, val seconds: Int) {

    constructor(sec: Int): this(
        sec / (60 * 60),
        (sec / (60)) % 60,
        sec % 60
    )

    fun getTotalSeconds(): Int {
        return hours * 60 * 60 + minutes * 60 + seconds
    }

    override fun toString(): String {
        val hoursString = hours.toString().padStart(2, '0')
        val minutesString = minutes.toString().padStart(2, '0')
        val secondsString = seconds.toString().padStart(2, '0')
        return "$hoursString : $minutesString : $secondsString"
    }
}