package be.ugent.sel.studeez.data.local.models.timer_functional

class HoursMinutesSeconds(val hours: Int, val minutes: Int, val seconds: Int) {
    constructor(seconds: Int) : this(
        hours = seconds / (60 * 60),
        minutes = (seconds / 60) % 60,
        seconds = seconds % 60,
    )

    override fun toString(): String {
        return hours.toString().padStart(2, '0') +
                ":" +
                minutes.toString().padStart(2, '0') +
                ":" +
                seconds.toString().padStart(2, '0')
    }
}