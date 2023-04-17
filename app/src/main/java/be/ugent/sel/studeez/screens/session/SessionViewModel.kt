package be.ugent.sel.studeez.screens.session

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    logService: LogService
) : StudeezViewModel(logService) {

    private val timer: FunctionalTimer = FunctionalPomodoroTimer(15, 5, 3)

    fun getTimer() : FunctionalTimer {
        return timer
    }
}