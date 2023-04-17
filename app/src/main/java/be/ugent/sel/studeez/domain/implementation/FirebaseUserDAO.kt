package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
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

    override suspend fun getUsername(): String? {
        return currentUserDocument().get().await().getString("username")
    }

    override suspend fun save(newUsername: String) {
        currentUserDocument().set(mapOf("username" to newUsername))
    }

    private fun currentUserDocument(): DocumentReference =
        firestore.collection(USER_COLLECTION).document(auth.currentUserId)

    companion object {
        private const val USER_COLLECTION = "users"
    }

    override suspend fun deleteUserReferences() {
        currentUserDocument().delete()
            .addOnSuccessListener { SnackbarManager.showMessage(R.string.success) }
            .addOnFailureListener { SnackbarManager.showMessage(R.string.generic_error) }
    }
}