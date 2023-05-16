package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.data.remote.FirebaseSessionReport
import be.ugent.sel.studeez.data.remote.FirebaseSessionReport.ENDTIME
import be.ugent.sel.studeez.data.remote.FirebaseSessionReport.STUDYTIME
import be.ugent.sel.studeez.domain.*
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.SESSION_COLLECTION
import be.ugent.sel.studeez.domain.implementation.FirebaseCollections.USER_COLLECTION
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseSessionDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
    private val userDAO: UserDAO,
    private val friendshipDAO: FriendshipDAO,
    private val taskDAO: TaskDAO,
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

    override fun getFriendsSessions(): Flow<List<Pair<String, List<Task>>>> {
        return friendshipDAO.getAllFriendships(auth.currentUserId)
            .map { friendships ->
                friendships.map { friendship ->
                    val userId: String = friendship.friendId
                    val username = userDAO.getUsername(userId)
                    val userTasks = getSessionsOfUser(userId)
                        .map { sessionReport ->
                            taskDAO.getTaskFromUser(
                                sessionReport.subjectId,
                                sessionReport.taskId,
                                userId
                            )
                        }
                    Pair(username, userTasks)
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