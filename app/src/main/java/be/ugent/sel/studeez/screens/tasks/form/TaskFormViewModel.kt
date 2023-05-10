package be.ugent.sel.studeez.screens.tasks.form

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.SelectedSubject
import be.ugent.sel.studeez.data.SelectedTask
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TaskDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TaskFormViewModel(
    protected val taskDAO: TaskDAO,
    protected val selectedSubject: SelectedSubject,
    protected val selectedTask: SelectedTask,
    logService: LogService,
) : StudeezViewModel(logService) {
    abstract val uiState: MutableState<TaskFormUiState>

    protected val name: String
        get() = uiState.value.name

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }
}

@HiltViewModel
class TaskCreateFormViewModel @Inject constructor(
    taskDAO: TaskDAO,
    selectedSubject: SelectedSubject,
    selectedTask: SelectedTask,
    logService: LogService,
) : TaskFormViewModel(taskDAO, selectedSubject, selectedTask, logService) {
    override val uiState = mutableStateOf(TaskFormUiState())

    fun onCreate(openAndPopUp: (String, String) -> Unit) {
        val newTask = Task(name = name, subjectId = selectedSubject().id)
        taskDAO.saveTask(newTask)
        openAndPopUp(StudeezDestinations.TASKS_SCREEN, StudeezDestinations.ADD_TASK_FORM)
    }
}

@HiltViewModel
class TaskEditFormViewModel @Inject constructor(
    taskDAO: TaskDAO,
    selectedSubject: SelectedSubject,
    selectedTask: SelectedTask,
    logService: LogService,
) : TaskFormViewModel(taskDAO, selectedSubject, selectedTask, logService) {
    override val uiState = mutableStateOf(TaskFormUiState())

    fun onDelete(openAndPopUp: (String, String) -> Unit) {
        taskDAO.deleteTask(selectedTask())
        openAndPopUp(StudeezDestinations.TASKS_SCREEN, StudeezDestinations.EDIT_TASK_FORM)
    }

    fun onEdit(openAndPopUp: (String, String) -> Unit) {
        val newTask = selectedTask().copy(name = name)
        taskDAO.updateTask(newTask)
        openAndPopUp(StudeezDestinations.TASKS_SCREEN, StudeezDestinations.EDIT_TASK_FORM)
    }
}
