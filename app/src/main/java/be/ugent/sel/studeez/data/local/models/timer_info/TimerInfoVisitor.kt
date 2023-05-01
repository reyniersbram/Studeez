package be.ugent.sel.studeez.data.local.models.timer_info

interface TimerInfoVisitor<T> {

    fun visitCustomTimerInfo(customTimerInfo: CustomTimerInfo): T

    fun visitEndlessTimerInfo(endlessTimerInfo: EndlessTimerInfo): T

    fun visitBreakTimerInfo(pomodoroTimerInfo: PomodoroTimerInfo): T

}