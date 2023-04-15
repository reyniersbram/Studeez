package be.ugent.sel.studeez.domain

interface UserDAO {

    suspend fun getUsername(): String?
    suspend fun save(newUsername: String)
}