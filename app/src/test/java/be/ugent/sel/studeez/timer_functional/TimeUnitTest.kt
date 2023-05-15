package be.ugent.sel.studeez.timer_functional

import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.data.local.models.timer_functional.Time
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TimeUnitTest {
    private val hours = 4
    private val minutes = 20
    private val seconds = 39
    private var time: Time = Time(seconds + minutes * 60 + hours * 60 * 60)

    @Before
    fun setup() {

    }

    @Test
    fun formatTime() {
        Assert.assertEquals(
            HoursMinutesSeconds(
                hours,
                minutes,
                seconds,
            ),
            time.getAsHMS(),
        )
    }

    @Test
    fun getTime() {
        Assert.assertEquals(
            seconds + minutes * 60 + hours * 60 * 60,
            time.time,
        )
    }

    @Test
    fun minOne() {
        Assert.assertEquals(
            (seconds + minutes * 60 + hours * 60 * 60),
            time.time,
        )
        time--
        Assert.assertEquals(
            (seconds + minutes * 60 + hours * 60 * 60) - 1,
            time.time,
        )
    }

    @Test
    fun plusOne() {
        time++
        Assert.assertEquals(
            (seconds + minutes * 60 + hours * 60 * 60) + 1,
            time.time,
        )
    }

    @Test
    fun minMultiple() {
        val n = 10
        for (i in 1 .. n) {
            time--
        }
        Assert.assertEquals(
            (seconds + minutes * 60 + hours * 60 * 60) - n,
            time.time,
        )
    }

    @Test
    fun plusMultiple() {
        val n = 10
        for (i in 1 .. n) {
            time++
        }
        Assert.assertEquals(
            (seconds + minutes * 60 + hours * 60 * 60) + n,
            time.time,
        )
    }
}