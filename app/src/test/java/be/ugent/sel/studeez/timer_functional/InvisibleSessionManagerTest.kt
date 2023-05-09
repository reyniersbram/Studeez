package be.ugent.sel.studeez.timer_functional

import android.media.MediaPlayer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
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
    private var timerState: SelectedTimer = SelectedTimer()
    private lateinit var viewModel: SessionViewModel
    private var mediaPlayer: MediaPlayer = mock()

    @Test
    fun InvisibleEndlessTimerTest() = runTest {
        timerState.selectedTimer = FunctionalEndlessTimer()
        viewModel = SessionViewModel(timerState, SessionReport(), mock())
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
        viewModel = SessionViewModel(timerState, SessionReport(), mock())
        InvisibleSessionManager.setParameters(viewModel, mediaPlayer)

        val test = launch {
            InvisibleSessionManager.updateTimer()
        }

        Assert.assertEquals(viewModel.getTimer().time.time, 10)
        advanceTimeBy(1_000) // start tikker

        advanceTimeBy(9_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 1)
        // focus, 9 sec, 1 sec nog

        advanceTimeBy(2_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 4)
        // pauze, 11 sec bezig, 4 seconden nog pauze

        advanceTimeBy(5_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 9)
        // 2e focus, 16 sec, 9 sec in 2e focus nog

        advanceTimeBy(13_000)
        Assert.assertTrue(viewModel.getTimer().hasEnded())
        // Done

        test.cancel()
        return@runTest
    }

    @Test
    fun InvisibleCustomTimerTest() = runTest {
        timerState.selectedTimer = FunctionalCustomTimer(5)
        viewModel = SessionViewModel(timerState, SessionReport(), mock())
        InvisibleSessionManager.setParameters(viewModel, mediaPlayer)

        val test = launch {
            InvisibleSessionManager.updateTimer()
        }

        Assert.assertEquals(viewModel.getTimer().time.time, 5)
        advanceTimeBy(1_000) // Start tikker
        advanceTimeBy(4_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 1)
        advanceTimeBy(1_000)
        Assert.assertEquals(viewModel.getTimer().time.time, 0)

        test.cancel()
        return@runTest
    }
}