package be.ugent.sel.studeez

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import org.junit.Assert
import org.junit.Test

class FuntionalPomodoroTimerUnitTest : FunctionalTimerUnitTest() {
    private val breakTime = 10
    private val breaks = 2
    override val hours = 0
    override val minutes = 0
    override val seconds = 10
    private lateinit var pomodoroTimer: FunctionalPomodoroTimer

    override fun setTimer() {
        pomodoroTimer = FunctionalPomodoroTimer(time, breakTime, breaks)
    }

    override fun testOneTick() {
        pomodoroTimer.tick()
        Assert.assertEquals(
            time - 1,
            pomodoroTimer.time.time,
        )
        Assert.assertFalse(pomodoroTimer.isInBreak)
        Assert.assertEquals(
            breaks,
            pomodoroTimer.breaksRemaining,
        )
        Assert.assertEquals(
            FunctionalTimer.FOCUS,
            pomodoroTimer.view,
        )
    }

    override fun multipleTicks() {
        val n = 10
        for (i in 1..n) {
            pomodoroTimer.tick()
        }
        Assert.assertEquals(
            time - n,
            pomodoroTimer.time.time
        )
    }

    override fun testEnded() {
        pomodoroTimer = FunctionalPomodoroTimer(0, 0, 0)
        pomodoroTimer.tick()
        Assert.assertEquals(
            FunctionalTimer.DONE,
            pomodoroTimer.view,
        )
    }

    @Test
    fun switchToBreak() {
        for (i in 0..10) {
            pomodoroTimer.tick()
        }
        Assert.assertTrue(pomodoroTimer.isInBreak)
        Assert.assertEquals(
            FunctionalTimer.BREAK,
            pomodoroTimer.view
        )
    }

    @Test
    fun switchToStudying() {
        for (i in 0..time) {
            pomodoroTimer.tick()
        }
        Assert.assertTrue(pomodoroTimer.isInBreak)
        Assert.assertEquals(
            FunctionalTimer.BREAK,
            pomodoroTimer.view
        )
        for (i in 0..breakTime) {
            pomodoroTimer.tick()
        }
        Assert.assertFalse(pomodoroTimer.isInBreak)
        val breaksRemaining = breaks - 1
        Assert.assertEquals(
            breaksRemaining,
            pomodoroTimer.breaksRemaining
        )
        Assert.assertEquals(
            FunctionalTimer.FOCUS_REMAINING(breaksRemaining),
            pomodoroTimer.view
        )
    }
}