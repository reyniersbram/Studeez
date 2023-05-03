package be.ugent.sel.studeez.screens.tasks

import be.ugent.sel.studeez.data.SelectedSubject
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.SubjectDAO
import be.ugent.sel.studeez.domain.TaskDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDAO: TaskDAO,
    private val subjectDAO: SubjectDAO,
    private val selectedSubject: SelectedSubject,
    logService: LogService,
) : StudeezViewModel(logService) {
    fun addTask() {

    }

    fun getTasks(): Flow<List<Task>> {
        return taskDAO.getTasks(selectedSubject())
    }

    fun deleteSubject(open: (String) -> Unit) {
        subjectDAO.deleteSubject(selectedSubject())
        open(StudeezDestinations.SUBJECT_SCREEN)
    }

    fun getSelectedSubject(): Subject {
        return selectedSubject()
    }

    fun deleteTask(task: Task) {
        taskDAO.deleteTask(task)
    }

    fun toggleTaskCompleted(task: Task, completed: Boolean) {
        taskDAO.toggleTaskCompleted(task, completed)
    }

    fun editSubject(open: (String) -> Unit) {
        open(StudeezDestinations.EDIT_SUBJECT_FORM)
    }
}