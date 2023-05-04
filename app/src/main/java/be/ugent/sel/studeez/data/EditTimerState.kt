package be.ugent.sel.studeez.data

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditTimerState @Inject constructor(){
    lateinit var timerInfo: TimerInfo
}