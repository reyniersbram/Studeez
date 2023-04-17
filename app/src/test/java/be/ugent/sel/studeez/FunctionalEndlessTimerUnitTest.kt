package be.ugent.sel.studeez

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import org.junit.Assert

class FunctionalEndlessTimerUnitTest : FunctionalTimerUnitTest() {
    override fun setTimer() {
        timer = FunctionalEndlessTimer()
    }

    override fun testOneTick() {
        timer.tick()
        Assert.assertEquals(
            1,
            timer.time.time
        )
    }

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

    override fun testEnded() {
        val n = 1000
        for (i in 1..n) {
            timer.tick()
            Assert.assertEquals(
                FunctionalTimer.FOCUS,
                timer.view
            )
        }
    }
}