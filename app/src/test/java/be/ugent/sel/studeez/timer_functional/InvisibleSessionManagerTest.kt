package be.ugent.sel.studeez.timer_functional

import android.media.MediaPlayer
import be.ugent.sel.studeez.data.SelectedTimerState
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.screens.session.InvisibleSessionManager
import be.ugent.sel.studeez.screens.session.SessionViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class InvisibleSessionManagerTest {
    private var timerState: SelectedTimerState = SelectedTimerState()
    private lateinit var viewModel: SessionViewModel
    private val mediaPlayer = mock<MediaPlayer>()

    @Test
    fun InvisibleEndlessTimerTest() = runTest {
        timerState.selectedTimer = FunctionalEndlessTimer()
        viewModel = SessionViewModel(timerState, mock())
        InvisibleSessionManager.setParameters(viewModel, mediaPlayer)

        val test = launch {
            InvisibleSessionManager.updateTimer()
        }

        Assert.assertEquals(viewModel.getTimer().time.time, 0)
        advanceTimeBy(1_000) // Start tikker
        advanceTimeBy(10_000_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 10000)

        test.cancel()
        return@runTest
    }

    @Test
    fun InvisiblePomodoroTimerTest() = runTest {
        val studyTime = 10
        val breakTime = 5
        val repeats = 1
        timerState.selectedTimer = FunctionalPomodoroTimer(studyTime, breakTime, repeats)
        viewModel = SessionViewModel(timerState, mock())
        InvisibleSessionManager.setParameters(viewModel, mediaPlayer)

        val test = launch {
            InvisibleSessionManager.updateTimer()
        }

        Assert.assertEquals(viewModel.getTimer().time.time, 10)
        advanceTimeBy(1_000) // start tikker

        advanceTimeBy(9_000)
        Assert.assertEquals(viewModel.getTimer().view, FunctionalTimer.StudyState.FOCUS) // Tijdens het focussen

        advanceTimeBy(1_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 0) // Focussen gedaan

        advanceTimeBy(4_000)
        Assert.assertEquals(viewModel.getTimer().view, FunctionalTimer.StudyState.BREAK) // Tijdens pauze

        advanceTimeBy(1_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 0) // Pauze gedaan

        advanceTimeBy(9_000)
        Assert.assertEquals(viewModel.getTimer().view, FunctionalTimer.StudyState.FOCUS_REMAINING) // Tijdens 2e focus

        advanceTimeBy(1_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 0) // 2e focus gedaan

        advanceTimeBy(4_000)
        Assert.assertEquals(viewModel.getTimer().view, FunctionalTimer.StudyState.DONE) // Done

        test.cancel()
        return@runTest
    }
}