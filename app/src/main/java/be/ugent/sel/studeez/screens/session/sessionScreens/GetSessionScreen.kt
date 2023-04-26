package be.ugent.sel.studeez.screens.session.sessionScreens

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimerVisitor

class GetSessionScreen : FunctionalTimerVisitor<AbstractSessionScreen> {
    override fun visitFunctionalCustomTimer(functionalCustomTimer: FunctionalCustomTimer): AbstractSessionScreen =
        CustomSessionScreen(functionalCustomTimer)

    override fun visitFunctionalEndlessTimer(functionalEndlessTimer: FunctionalEndlessTimer): AbstractSessionScreen =
        EndlessSessionScreen()

    override fun visitFunctionalBreakTimer(functionalPomodoroTimer: FunctionalPomodoroTimer): AbstractSessionScreen =
        BreakSessionScreen(functionalPomodoroTimer)
}