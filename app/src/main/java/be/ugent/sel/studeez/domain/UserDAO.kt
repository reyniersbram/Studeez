package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.User

interface UserDAO {

    suspend fun getUser(): User

    suspend fun saveUser(
        newUsername: String,
        newBiography: String = ""
    )

    /**
     * Delete all references to this user in the database. Similar to the deleteCascade in
     * relational databases.
     */
    suspend fun deleteUserReferences()
}