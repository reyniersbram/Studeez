package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.UserDAO
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO
    ) : UserDAO {

    override suspend fun getUser(): User {
        val userDocument = currentUserDocument().get().await()
        return User(
            username = userDocument.getString(USERNAME_FIELD) ?: "",
            biography = userDocument.getString(BIOGRAPHY_FIELD) ?: ""
        )
    }

    override suspend fun saveUser(
        newUsername: String,
        newBiography: String
    ) {
        currentUserDocument().set(mapOf(
            USERNAME_FIELD to newUsername,
            BIOGRAPHY_FIELD to newBiography
        ))
    }

    private fun currentUserDocument(): DocumentReference =
        firestore.collection(USER_COLLECTION).document(auth.currentUserId)

    companion object {
        private const val USER_COLLECTION = "users"
        private const val USERNAME_FIELD = "username"
        private const val BIOGRAPHY_FIELD = "biography"
    }

    override suspend fun deleteUserReferences() {
        currentUserDocument().delete()
            .addOnSuccessListener { SnackbarManager.showMessage(R.string.success) }
            .addOnFailureListener { SnackbarManager.showMessage(R.string.generic_error) }
    }
}