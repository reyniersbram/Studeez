package be.ugent.sel.studeez.screens.tasks.form

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
    private val taskDAO: TaskDAO,
    private val selectedSubject: SelectedSubject,
    private val selectedTask: SelectedTask,
    logService: LogService,
) : StudeezViewModel(logService) {
    var uiState = mutableStateOf(
        if (selectedTask.isSet()) TaskFormUiState(selectedTask().name) else TaskFormUiState()
    )
        private set

    private val name: String
        get() = uiState.value.name

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onDelete(openAndPopUp: (String, String) -> Unit) {
        taskDAO.deleteTask(selectedTask())
        openAndPopUp(StudeezDestinations.TASKS_SCREEN, StudeezDestinations.EDIT_TASK_FORM)
    }

    fun onCreate(openAndPopUp: (String, String) -> Unit) {
        val newTask = Task(name = name, subjectId = selectedSubject().id)
        taskDAO.saveTask(newTask)
        openAndPopUp(StudeezDestinations.TASKS_SCREEN, StudeezDestinations.ADD_TASK_FORM)
    }

    fun onEdit(openAndPopUp: (String, String) -> Unit) {
        val newTask = Task(name = name)
        taskDAO.updateTask(newTask)
        openAndPopUp(StudeezDestinations.TASKS_SCREEN, StudeezDestinations.EDIT_TASK_FORM)
    }
}