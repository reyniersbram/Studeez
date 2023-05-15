package be.ugent.sel.studeez.domain

interface UserDAO {

    suspend fun getUsername(): String?
    suspend fun save(newUsername: String)

    /**
     * Delete all references to this user in the database. Similar to the deleteCascade in
     * relational databases.
     */
    suspend fun deleteUserReferences()
}