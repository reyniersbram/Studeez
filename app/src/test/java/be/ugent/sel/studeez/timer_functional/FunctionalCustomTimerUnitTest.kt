package be.ugent.sel.studeez.timer_functional

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import org.junit.Assert
import org.junit.Test

class FunctionalCustomTimerUnitTest : FunctionalTimerUnitTest() {
    override fun setTimer() {
        timer = FunctionalCustomTimer(time)
    }

    @Test
    override fun testOneTick() {
        timer.tick()
        Assert.assertEquals(
            time - 1,
            timer.time.time,
        )
    }

    @Test
    override fun multipleTicks() {
        val n = 10
        for (i in 1..n) {
            timer.tick()
        }
        Assert.assertEquals(
            time - n,
            timer.time.time,
        )
    }

    @Test
    override fun testEnded() {
        timer = FunctionalCustomTimer(0)
        timer.tick()
        Assert.assertTrue(timer.hasEnded())
        Assert.assertTrue(timer.hasEnded())
    }
}