package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import kotlinx.coroutines.flow.Flow

interface TaskDAO {

    fun getTasks(subject: Subject): Flow<List<Task>>

    fun saveTask(newTask: Task)

    fun updateTask(newTask: Task)

    fun deleteTask(oldTask: Task)

    suspend fun getTask(subjectId: String, taskId: String): Task
}