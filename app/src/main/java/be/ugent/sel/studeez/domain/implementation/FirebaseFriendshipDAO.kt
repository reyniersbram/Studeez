package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.data.local.models.Friendship
import be.ugent.sel.studeez.data.remote.FirebaseFriendship.ACCEPTED
import be.ugent.sel.studeez.data.remote.FirebaseFriendship.FRIENDID
import be.ugent.sel.studeez.data.remote.FirebaseFriendship.FRIENDSSINCE
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.FriendshipDAO
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.FRIENDS_COLLECTION
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.USER_COLLECTION
import com.google.firebase.Timestamp
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
) : FriendshipDAO {

    private fun currentUserDocument(): DocumentReference = firestore
        .collection(USER_COLLECTION)
        .document(auth.currentUserId)

    override fun getAllFriendships(
        userId: String
    ): Flow<List<Friendship>> {
        return firestore
            .collection(USER_COLLECTION)
            .document(userId)
            .collection(FRIENDS_COLLECTION)
            .snapshots()
            .map { it.toObjects(Friendship::class.java) }
    }

    override fun getFriendshipCount(
        userId: String
    ): Flow<Int> {
        return flow {
            val friendshipCount = suspendCoroutine { continuation ->
                firestore
                    .collection(USER_COLLECTION)
                    .document(userId)
                    .collection(FRIENDS_COLLECTION)
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
        val currentUserId: String = auth.currentUserId
        val otherUserId: String = id

        // Check if the friendship already exists for the logged in user
        var allowed = false
        firestore.collection(USER_COLLECTION)
            .document(currentUserId)
            .collection(FRIENDS_COLLECTION)
            .whereEqualTo(FRIENDID, otherUserId)
            .get()
            .addOnSuccessListener {
                allowed = it.documents.isEmpty()

                if (allowed) {
                    // Add entry to current user
                    currentUserDocument()
                        .collection(FRIENDS_COLLECTION)
                        .add(
                            mapOf(
                                FRIENDID to otherUserId,
                                ACCEPTED to true, // TODO Make it not automatically accepted.
                                FRIENDSSINCE to Timestamp.now()
                            )
                        )

                    // Add entry to other user
                    firestore.collection(USER_COLLECTION)
                        .document(otherUserId)
                        .collection(FRIENDS_COLLECTION)
                        .add(
                            mapOf(
                                FRIENDID to currentUserId,
                                ACCEPTED to true, // TODO Make it not automatically accepted.
                                FRIENDSSINCE to Timestamp.now()
                            )
                        )
                }
            }.addOnSuccessListener {
                val message = if (allowed) AppText.success else AppText.already_friend
                SnackbarManager.showMessage(message)
            }

        return true
    }

    override fun acceptFriendship(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeFriendship(
        friendship: Friendship
    ): Boolean {
        val currentUserId: String = auth.currentUserId
        val otherUserId: String = friendship.friendId

        // Remove at logged in user
        firestore.collection(USER_COLLECTION)
            .document(currentUserId)
            .collection(FRIENDS_COLLECTION)
            .document(friendship.id)
            .delete()

        // Remove at other user
        firestore.collection(USER_COLLECTION)
            .document(otherUserId)
            .collection(FRIENDS_COLLECTION)
            .whereEqualTo(FRIENDID, currentUserId)
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    document.reference.delete()
                }
            }.addOnFailureListener {
                SnackbarManager.showMessage(AppText.generic_error)
            }

        return true
    }

}