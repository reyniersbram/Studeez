package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.UserDAO
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO
    ) : UserDAO {

    companion object {
        private const val USER_COLLECTION = FirebaseCollections.USER_COLLECTION
        private const val USERNAME_FIELD = "username"
        private const val BIOGRAPHY_FIELD = "biography"
    }

    private fun currentUserDocument(): DocumentReference =
        firestore
            .collection(USER_COLLECTION)
            .document(auth.currentUserId)

    override fun getAllUsers(): Flow<List<User>> {
        return firestore
            .collection(FirebaseCollections.USER_COLLECTION)
            .snapshots()
            .map { it.toObjects(User::class.java) }
    }

    override fun getUsersWithQuery(): Flow<List<User>> {
        TODO("Not yet implemented")
    }

    override fun getUserDetails(userId: String): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getLoggedInUser(): User {
        val userDocument = currentUserDocument().get().await()
        return User(
            username = userDocument.getString(USERNAME_FIELD) ?: "",
            biography = userDocument.getString(BIOGRAPHY_FIELD) ?: ""
        )
    }

    override suspend fun saveLoggedInUser(
        newUsername: String,
        newBiography: String
    ) {
        currentUserDocument().set(mapOf(
            USERNAME_FIELD to newUsername,
            BIOGRAPHY_FIELD to newBiography
        ))
    }

    override suspend fun deleteLoggedInUserReferences() {
        currentUserDocument().delete()
            .addOnSuccessListener { SnackbarManager.showMessage(R.string.success) }
            .addOnFailureListener { SnackbarManager.showMessage(R.string.generic_error) }
    }
}