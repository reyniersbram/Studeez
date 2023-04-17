package be.ugent.sel.studeez

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import org.junit.Before
import org.junit.Test

abstract class FunctionalTimerUnitTest {
    protected lateinit var timer: FunctionalTimer
    private val hours = 4
    private val minutes = 20
    private val seconds = 39
    protected val time = seconds + minutes * 60 + hours * 60 * 60

    @Before
    abstract fun setup()

    @Test
    abstract fun testOneTick()

    @Test
    abstract fun multipleTicks()

    @Test
    abstract fun testEnded()
}