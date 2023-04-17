package be.ugent.sel.studeez

import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.data.local.models.timer_functional.Time
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TimeUnitTest {
    private val hours = 4
    private val minutes = 20
    private val seconds = 39
    private val time: Time = Time(seconds + minutes * 60 + hours * 60 * 60)

    @Before
    fun setup() {

    }

    @Test
    fun formatTime() {
        Assert.assertEquals(
            time.getAsHMS(),
            HoursMinutesSeconds(
                hours.toString().padStart(2, '0'),
                minutes.toString().padStart(2, '0'),
                seconds.toString().padStart(2, '0'),
            ),
        )
    }

    @Test
    fun getTime() {
        Assert.assertEquals(
            time.time,
            seconds + minutes * 60 + hours * 60 * 60
        )
    }

    @Test
    fun minOne() {
        time.minOne()
        Assert.assertEquals(
            time.time,
            (seconds + minutes * 60 + hours * 60 * 60) - 1
        )
    }

    @Test
    fun plusOne() {
        time.plusOne()
        Assert.assertEquals(
            time.time,
            (seconds + minutes * 60 + hours * 60 * 60) + 1
        )
    }

    @Test
    fun minMultiple() {
        val n = 10
        for (i in 1..n) {
            time.minOne()
        }
        Assert.assertEquals(
            time.time,
            (seconds + minutes * 60 + hours * 60 * 60) - n
        )
    }

    @Test
    fun plusMultiple() {
        val n = 10
        for (i in 1..n) {
            time.plusOne()
        }
        Assert.assertEquals(
            time.time,
            (seconds + minutes * 60 + hours * 60 * 60) + n
        )
    }
}