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
        return HoursMinutesSeconds(time)
    }
}