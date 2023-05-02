package be.ugent.sel.studeez.screens.tasks

import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TaskDAO
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDAO: TaskDAO,
    logService: LogService,
) : StudeezViewModel(logService) {
    fun addTask() {

    }

    fun getTasks() : Flow<List<Task>> {
        return flowOf(listOf(
            Task(
                name = "Test Task",
                completed = false,
            ),
            Task(
                name = "Test Task 2",
                completed = true,
            )
        ))
    }
}