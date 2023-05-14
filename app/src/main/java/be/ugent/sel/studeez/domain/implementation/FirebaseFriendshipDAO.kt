package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.data.local.models.Friendship
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.FriendshipDAO
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import be.ugent.sel.studeez.R.string as AppText

class FirebaseFriendshipDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO
): FriendshipDAO {

    private fun currentUserDocument(): DocumentReference = firestore
        .collection(FirebaseCollections.USER_COLLECTION)
        .document(auth.currentUserId)

    override fun getAllFriendships(): Flow<List<Friendship>> {
        return currentUserDocument()
            .collection(FirebaseCollections.FRIENDS_COLLECTION)
            .snapshots()
            .map { it.toObjects(Friendship::class.java) }
    }

    override fun getFriendshipCount(): Flow<Int> {
        return flow {
            val friendshipCount = suspendCoroutine { continuation ->
                currentUserDocument()
                    .collection(FirebaseCollections.FRIENDS_COLLECTION)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        continuation.resume(querySnapshot.size())
                    }
                    .addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
            }
            emit(friendshipCount)
        }.catch {
            SnackbarManager.showMessage(AppText.generic_error)
        }
    }

    override fun getFriendshipDetails(id: String): Friendship {
        TODO("Not yet implemented")
    }

    override fun sendFriendshipRequest(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun acceptFriendship(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeFriendship(id: String): Boolean {
        TODO("Not yet implemented")
    }

}