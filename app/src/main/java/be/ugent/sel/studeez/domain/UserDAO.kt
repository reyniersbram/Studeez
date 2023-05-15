package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.User
import kotlinx.coroutines.flow.Flow

interface UserDAO {

    fun getCurrentUserId(): String

    /**
     * @return all users
     */
    fun getAllUsers(): Flow<List<User>>

    /**
     * @return all users based on a query, a trimmed down version of getAllUsers()
     */
    fun getUsersWithQuery(
        fieldName: String,
        value: String
    ): Flow<List<User>>

    /**
     * Request information about a user
     */
    fun getUserDetails(
        userId: String
    ): Flow<User>

    suspend fun getUsername(
        userId: String
    ): String

    /**
     * @return information on the currently logged in user.
     */
    suspend fun getLoggedInUser(): User
    // TODO Should be refactored to fun getLoggedInUser(): Flow<User>, without suspend.

    suspend fun saveLoggedInUser(
        newUsername: String,
        newBiography: String = ""
    )
    // TODO Should be refactored to fun saveLoggedInUser(...): Boolean, without suspend.

    /**
     * Delete all references to the logged in user in the database. Similar to the deleteCascade in
     * relational databases.
     */
    suspend fun deleteLoggedInUserReferences()
    // TODO Should be refactored to fun deleteLoggedInUserReferences(): Boolean, without suspend.
}