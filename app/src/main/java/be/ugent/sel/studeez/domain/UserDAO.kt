package be.ugent.sel.studeez.domain

import kotlinx.coroutines.flow.Flow

interface UserDAO {

    suspend fun getUserName(): String?

    suspend fun save(newUsername: String)

//    suspend fun update(task: Task)
//    suspend fun delete(taskId: String)
//    suspend fun deleteAllForUser(userId: String)
}