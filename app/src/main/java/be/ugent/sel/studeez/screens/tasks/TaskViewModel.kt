package be.ugent.sel.studeez.screens.tasks

import be.ugent.sel.studeez.data.SelectedSubject
import be.ugent.sel.studeez.data.SelectedTask
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TaskDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDAO: TaskDAO,
    private val selectedSubject: SelectedSubject,
    private val selectedTask: SelectedTask,
    logService: LogService,
) : StudeezViewModel(logService) {
    fun addTask(open: (String) -> Unit) {
        open(StudeezDestinations.ADD_TASK_FORM)
    }

    fun getTasks(): Flow<List<Task>> {
        return taskDAO.getTasks(selectedSubject())
    }

    fun getSelectedSubject(): Subject {
        return selectedSubject()
    }

    fun deleteTask(task: Task) {
        taskDAO.deleteTask(task)
    }

    fun archiveTask(task: Task) {
        taskDAO.updateTask(task.copy(archived = true))
    }

    fun toggleTaskCompleted(task: Task, completed: Boolean) {
        taskDAO.updateTask(task.copy(completed = completed))
    }

    fun editSubject(open: (String) -> Unit) {
        open(StudeezDestinations.EDIT_SUBJECT_FORM)
    }

    fun startTask(task: Task, open: (String) -> Unit) {
        selectedTask.set(task)
        open(StudeezDestinations.TIMER_SELECTION_SCREEN)
    }
}