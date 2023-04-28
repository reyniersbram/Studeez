package be.ugent.sel.studeez.screens.session

import be.ugent.sel.studeez.data.SelectedTimerState
import be.ugent.sel.studeez.data.SessionReportState
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val selectedTimerState: SelectedTimerState,
    private val sessionReportState: SessionReportState,
    logService: LogService
) : StudeezViewModel(logService) {

    private val task : String = "No task selected" // placeholder for tasks implementation

    fun getTimer() : FunctionalTimer {
        return selectedTimerState.selectedTimer!!
    }

    fun getTask(): String {
        return task
    }

    fun endSession(openAndPopUp: (String, String) -> Unit) {
        sessionReportState.sessionReport = getTimer().getSessionReport()
        openAndPopUp(StudeezDestinations.SESSION_RECAP, StudeezDestinations.SESSION_SCREEN)
    }
}