package be.ugent.sel.studeez.timer_functional

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import org.junit.Assert
import org.junit.Test

class FunctionalEndlessTimerUnitTest : FunctionalTimerUnitTest() {
    override fun setTimer() {
        timer = FunctionalEndlessTimer()
    }

    @Test
    override fun testOneTick() {
        timer.tick()
        Assert.assertEquals(
            1,
            timer.time.time
        )
    }

    @Test
    override fun multipleTicks() {
        val n = 10
        for (i in 1..n) {
            timer.tick()
        }
        Assert.assertEquals(
            n,
            timer.time.time
        )
    }

    @Test
    override fun testEnded() {
        val n = 1000
        for (i in 1..n) {
            timer.tick()
            Assert.assertFalse(timer.hasEnded())
            Assert.assertEquals(
                FunctionalTimer.FOCUS,
                timer.view
            )
        }
    }
}