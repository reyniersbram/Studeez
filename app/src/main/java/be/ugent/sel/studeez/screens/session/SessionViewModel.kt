package be.ugent.sel.studeez.screens.session

import be.ugent.sel.studeez.data.SelectedSessionReport
import be.ugent.sel.studeez.data.SelectedTask
import be.ugent.sel.studeez.data.SelectedTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val selectedTimer: SelectedTimer,
    private val sessionReport: SelectedSessionReport,
    private val selectedTask: SelectedTask,
    logService: LogService
) : StudeezViewModel(logService) {
    fun getTimer(): FunctionalTimer {
        return selectedTimer()
    }

    fun getTask(): String {
        return selectedTask().name
    }

    fun endSession(openAndPopUp: (String, String) -> Unit) {
        sessionReport.set(getTimer().getSessionReport(selectedTask().subjectId, selectedTask().id))
        openAndPopUp(StudeezDestinations.SESSION_RECAP, StudeezDestinations.SESSION_SCREEN)
    }
}