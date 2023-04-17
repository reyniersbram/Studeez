package be.ugent.sel.studeez.data.local.models.timer_functional

class Time(initialTime: Int) {

    private var time = initialTime

    fun minOne() {
        time--
    }

    fun plusOne() {
        time++
    }

    fun setTime(newTime: Int) {
        time = newTime
    }

    fun getTime(): Int {
        return time
    }

    fun getAsHMS(): HoursMinutesSeconds {
        val hours: Int = time / (60 * 60)
        val minutes: Int = (time / (60)) % 60
        val seconds: Int = time % 60

        return HoursMinutesSeconds(
            hours.toString().padStart(2, '0'),
            minutes.toString().padStart(2, '0'),
            seconds.toString().padStart(2, '0')
        )
    }

}