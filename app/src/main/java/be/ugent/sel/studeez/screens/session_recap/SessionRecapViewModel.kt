package be.ugent.sel.studeez.screens.session_recap

import be.ugent.sel.studeez.data.SelectedTask
import be.ugent.sel.studeez.data.SessionReportState
import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.SessionDAO
import be.ugent.sel.studeez.domain.TaskDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionRecapViewModel @Inject constructor(
    sessionReportState: SessionReportState,
    private val sessionDAO: SessionDAO,
    private val taskDAO: TaskDAO,
    private val selectedTask: SelectedTask,
    logService: LogService
) : StudeezViewModel(logService) {

    private val report: SessionReport = sessionReportState.sessionReport!!

    fun getSessionReport(): SessionReport {
        return report
    }

    fun saveSession(open: (String, String) -> Unit) {
        sessionDAO.saveSession(getSessionReport())
        val newTask = selectedTask().copy(time = selectedTask().time + report.studyTime)
        taskDAO.updateTask(newTask)
        open(StudeezDestinations.HOME_SCREEN, StudeezDestinations.SESSION_RECAP)
    }

    fun discardSession(open: (String, String) -> Unit) {
        open(StudeezDestinations.HOME_SCREEN, StudeezDestinations.SESSION_RECAP)
    }
}