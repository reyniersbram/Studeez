package be.ugent.sel.studeez.screens.session

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.screens.StudeezViewModel
import be.ugent.sel.studeez.data.SelectedTimerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val selectedTimerState: SelectedTimerState,
    logService: LogService
) : StudeezViewModel(logService) {

    fun getTimer() : FunctionalTimer {
        return selectedTimerState.selectedTimer!!
    }
}