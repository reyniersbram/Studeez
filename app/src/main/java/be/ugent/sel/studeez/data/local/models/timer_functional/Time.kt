package be.ugent.sel.studeez.data.local.models.timer_functional

class Time(var time: Int) {
    operator fun invoke() = time

    operator fun inc(): Time = Time(time + 1)

    operator fun dec(): Time = Time(time - 1)

    fun getAsHMS(): HoursMinutesSeconds {
        return HoursMinutesSeconds(time)
    }
}
