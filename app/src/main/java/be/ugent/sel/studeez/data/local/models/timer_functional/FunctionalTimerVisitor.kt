package be.ugent.sel.studeez.data.local.models.timer_functional

interface FunctionalTimerVisitor<T> {

    fun visitFunctionalCustomTimer(functionalCustomTimer: FunctionalCustomTimer): T

    fun visitFunctionalEndlessTimer(functionalEndlessTimer: FunctionalEndlessTimer): T

    fun visitFunctionalBreakTimer(functionalPomodoroTimer: FunctionalPomodoroTimer): T

}