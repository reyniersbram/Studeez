package be.ugent.sel.studeez.screens.session

import be.ugent.sel.studeez.data.SelectedTask
import be.ugent.sel.studeez.data.SelectedTimerState
import be.ugent.sel.studeez.data.SessionReportState
import be.ugent.sel.studeez.data.local.models.task.Task
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
    private val selectedTask: SelectedTask,
    logService: LogService
) : StudeezViewModel(logService) {

    private val task : Task = selectedTask()

    fun getTimer() : FunctionalTimer {
        return selectedTimerState.selectedTimer!!
    }

    fun getTask(): String {
        return task.name
    }

    fun endSession(openAndPopUp: (String, String) -> Unit) {
        sessionReportState.sessionReport = getTimer().getSessionReport(task.id)
        openAndPopUp(StudeezDestinations.SESSION_RECAP, StudeezDestinations.SESSION_SCREEN)
    }
}