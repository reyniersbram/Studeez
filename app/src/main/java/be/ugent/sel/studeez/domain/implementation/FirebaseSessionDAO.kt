package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.data.remote.FirebaseSessionReport
import be.ugent.sel.studeez.data.remote.FirebaseSessionReport.ENDTIME
import be.ugent.sel.studeez.data.remote.FirebaseSessionReport.STUDYTIME
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.FriendshipDAO
import be.ugent.sel.studeez.domain.SessionDAO
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.SESSION_COLLECTION
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.USER_COLLECTION
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseSessionDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
    private val userDAO: UserDAO,
    private val friendshipDAO: FriendshipDAO
) : SessionDAO {

    override fun getSessions(): Flow<List<SessionReport>> {
        return currentUserSessionsCollection()
            .snapshots()
            .map { it.toObjects(SessionReport::class.java) }
    }

    override suspend fun getSessionsOfUser(userId: String): List<SessionReport> {
        val collection = firestore.collection(USER_COLLECTION)
            .document(userId)
            .collection(SESSION_COLLECTION)
            .get().await()
        val list: MutableList<SessionReport> = mutableListOf()
        for (document in collection) {
            val id = document.id
            val studyTime: Int = document.getField<Int>(STUDYTIME)!!
            val endTime: Timestamp = document.getField<Timestamp>(ENDTIME)!!
            list.add(SessionReport(id, studyTime, endTime))
        }
        return list
    }

    override fun getFriendsSessions(): Flow<List<Pair<String, List<SessionReport>>>> {
        return friendshipDAO.getAllFriendships(auth.currentUserId)
            .map { friendships ->
                friendships.map { friendship ->
                    val userId: String = friendship.friendId
                    val username = userDAO.getUsername(userId)
                    val userSessions = getSessionsOfUser(userId)

                    Pair(username, userSessions)
                }
            }
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