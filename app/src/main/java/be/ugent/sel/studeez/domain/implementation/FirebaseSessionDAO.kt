package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.SessionDAO
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.SESSION_COLLECTION
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.USER_COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseSessionDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
) : SessionDAO {

    override fun getSessions(): Flow<List<SessionReport>> {
        return currentUserSessionsCollection()
            .snapshots()
            .map { it.toObjects(SessionReport::class.java) }
    }

    override suspend fun getSessionsOfUser(userId: String): List<SessionReport> {
        return firestore.collection(USER_COLLECTION)
            .document(userId)
            .collection(SESSION_COLLECTION)
            .get().await()
            .map { it.toObject(SessionReport::class.java) }
    }


    override fun saveSession(newSessionReport: SessionReport) {
        currentUserSessionsCollection().add(newSessionReport)
    }

    override fun deleteSession(newTimer: TimerInfo) {
        currentUserSessionsCollection().document(newTimer.id).delete()
    }

    private fun currentUserSessionsCollection(): CollectionReference =
        firestore.collection(USER_COLLECTION)
            .document(auth.currentUserId)
            .collection(SESSION_COLLECTION)
}