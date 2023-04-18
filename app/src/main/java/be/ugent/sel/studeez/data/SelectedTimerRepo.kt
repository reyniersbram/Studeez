package be.ugent.sel.studeez.screens.timers

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectedTimerRepo @Inject constructor(){
    var selectedTimer: FunctionalTimer? = null

}