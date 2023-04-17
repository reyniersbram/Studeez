package be.ugent.sel.studeez

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import org.junit.Before
import org.junit.Test

abstract class FunctionalTimerUnitTest {
    protected lateinit var timer: FunctionalTimer
    protected open val hours = 4
    protected open val minutes = 20
    protected open val seconds = 39
    protected var time: Int = 0

    @Before
    fun setup() {
        time = seconds + minutes * 60 + hours * 60 * 60
        setTimer()
    }

    /**
     * The timer-property should be set to the right implementation in this method.
     */
    abstract fun setTimer()

    @Test
    abstract fun testOneTick()

    @Test
    abstract fun multipleTicks()

    @Test
    abstract fun testEnded()
}