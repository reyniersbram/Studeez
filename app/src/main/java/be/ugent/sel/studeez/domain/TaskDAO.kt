package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.task.Task
import kotlinx.coroutines.flow.Flow

interface TaskDAO {

    fun getTasks(): Flow<List<Task>>

    fun saveTask(newTask: Task)

    fun deleteTask(oldTask: Task)
}