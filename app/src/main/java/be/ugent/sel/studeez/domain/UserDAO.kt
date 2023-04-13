package be.ugent.sel.studeez.domain

import kotlinx.coroutines.flow.Flow

interface UserDAO {

    suspend fun getUserName(): String?

    suspend fun save(newUsername: String)
}