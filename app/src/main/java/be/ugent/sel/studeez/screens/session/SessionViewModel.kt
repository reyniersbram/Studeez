package be.ugent.sel.studeez.screens.session

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.screens.StudeezViewModel
import be.ugent.sel.studeez.data.SelectedTimerRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val selectedTimerRepo: SelectedTimerRepo,
    logService: LogService
) : StudeezViewModel(logService) {

    fun getTimer() : FunctionalTimer {
        return selectedTimerRepo.selectedTimer!!
    }
}