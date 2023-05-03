package be.ugent.sel.studeez.data.local.models.timer_functional

class Time(initialTime: Int) {

    var time = initialTime

    fun minOne() {
        time--
    }

    fun plusOne() {
        time++
    }

    fun getAsHMS(): HoursMinutesSeconds {
        val hours: Int = time / (60 * 60)
        val minutes: Int = (time / (60)) % 60
        val seconds: Int = time % 60

        return HoursMinutesSeconds(hours, minutes, seconds)
    }

}