package be.ugent.sel.studeez

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import org.junit.Assert

class FunctionalCustomTimerUnitTest : FunctionalTimerUnitTest() {
    override fun setTimer() {
        timer = FunctionalCustomTimer(time)
    }

    override fun testOneTick() {
        timer.tick()
        Assert.assertEquals(
            time - 1,
            timer.time.time,
        )
    }

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

    override fun testEnded() {
        timer = FunctionalCustomTimer(0)
        timer.tick()
        Assert.assertEquals(
            FunctionalTimer.DONE,
            timer.view
        )
    }
}