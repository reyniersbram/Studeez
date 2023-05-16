package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.data.remote.FirebaseUser.BIOGRAPHY
import be.ugent.sel.studeez.data.remote.FirebaseUser.USERNAME
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.USER_COLLECTION
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO
) : UserDAO {

    override fun getCurrentUserId(): String {
        return auth.currentUserId
    }

    private fun currentUserDocument(): DocumentReference =
        firestore
            .collection(USER_COLLECTION)
            .document(auth.currentUserId)

    override fun getAllUsers(): Flow<List<User>> {
        return firestore
            .collection(USER_COLLECTION)
            .snapshots()
            .map { it.toObjects(User::class.java) }
    }

    override fun getUsersWithQuery(
        fieldName: String,
        value: String
    ): Flow<List<User>> {
        return firestore
            .collection(USER_COLLECTION)
            .whereEqualTo(fieldName, value)
            .snapshots()
            .map { it.toObjects(User::class.java) }
    }

    override fun getUserDetails(userId: String): Flow<User> {
        return flow {
            val snapshot = firestore
                .collection(USER_COLLECTION)
                .document(userId)
                .get()
                .await()
            val user = snapshot.toObject(User::class.java)!!
            emit(user)
        }
    }

    override suspend fun getUsername(userId: String): String {
        val user = firestore.collection(USER_COLLECTION)
            .document(userId)
            .get().await()
        return user.getString(USERNAME)!!
    }

    override suspend fun getLoggedInUser(): User {
        val userDocument = currentUserDocument().get().await()
        return User(
            username = userDocument.getString(USERNAME) ?: "",
            biography = userDocument.getString(BIOGRAPHY) ?: ""
        )
    }

    override suspend fun saveLoggedInUser(
        newUsername: String,
        newBiography: String
    ) {
        currentUserDocument().set(mapOf(
            USERNAME to newUsername,
            BIOGRAPHY to newBiography
        ))
    }

    override suspend fun deleteLoggedInUserReferences() {
        currentUserDocument().delete()
            .addOnSuccessListener { SnackbarManager.showMessage(R.string.success) }
            .addOnFailureListener { SnackbarManager.showMessage(R.string.generic_error) }
    }
}