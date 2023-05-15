package be.ugent.sel.studeez.screens.session.sessionScreens

import android.media.MediaPlayer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimerVisitor

class GetSessionScreen(private val mediaplayer: MediaPlayer?) : FunctionalTimerVisitor<AbstractSessionScreen> {
    override fun visitFunctionalCustomTimer(functionalCustomTimer: FunctionalCustomTimer): AbstractSessionScreen =
        CustomSessionScreen(functionalCustomTimer, mediaplayer)

    override fun visitFunctionalEndlessTimer(functionalEndlessTimer: FunctionalEndlessTimer): AbstractSessionScreen =
        EndlessSessionScreen()

    override fun visitFunctionalBreakTimer(functionalPomodoroTimer: FunctionalPomodoroTimer): AbstractSessionScreen =
        BreakSessionScreen(functionalPomodoroTimer, mediaplayer)
}