package be.ugent.sel.studeez.domain

import kotlinx.coroutines.flow.Flow

interface UserDAO {

    suspend fun getUserName(): String?

    suspend fun save(newUsername: String)

//    suspend fun updateTask(task: Task)
//    suspend fun deleteTask(taskId: String)
//    suspend fun deleteAllTasksOfUser(userId: String)
}