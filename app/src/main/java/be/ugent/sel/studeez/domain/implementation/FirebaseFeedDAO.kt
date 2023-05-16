package be.ugent.sel.studeez.domain.implementation

import android.icu.text.DateFormat
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.*
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseFeedDAO @Inject constructor(
    private val friendshipDAO: FriendshipDAO,
    private val sessionDAO: SessionDAO,
    private val taskDAO: TaskDAO,
    private val subjectDAO: SubjectDAO,
    private val auth: AccountDAO,
    private val userDAO: UserDAO,
) : FeedDAO {

    /**
     *  Return a map as with key the day and value a list of feedentries for that day.
     */
    override fun getFeedEntries(): Flow<Map<String, List<FeedEntry>>> {
        return sessionDAO.getSessions().map { sessionReports ->
            sessionReports
                .map { sessionReport -> sessionToFeedEntry(sessionReport) }
                .sortedByDescending { it.endTime }
                .groupBy { getFormattedTime(it) }
                .mapValues { (_, entries) ->
                    entries
                        .groupBy { it.taskId }
                        .map { fuseFeedEntries(it.component2()) }
                }
        }
    }

    /**
     *  Return a map as with key the day and value a list of feedentries for that day.
     */
    override suspend fun getFeedEntriesFromUser(id: String): Map<String, List<FeedEntry>> {
        return sessionDAO.getSessionsOfUser(id)
            .map { sessionReport -> sessionToFeedEntryFromUser(sessionReport, id) }
            .sortedByDescending { it.endTime }
            .groupBy { getFormattedTime(it) }
            .mapValues { (_, entries) ->
                entries
                    .groupBy { it.taskId }
                    .map { fuseFeedEntries(it.component2()) }
            }
    }

    override fun getFriendsSessions(): Flow<Map<String, List<Pair<String, FeedEntry>>>> {
        return friendshipDAO.getAllFriendships(auth.currentUserId)
            .map { friendships ->
                friendships.map { friendship ->
                    val userId: String = friendship.friendId
                    val username = userDAO.getUsername(userId)
                    val friendFeed = getFeedEntriesFromUser(userId)
                    Pair(username, friendFeed)
                }
            }.map {
                mergeNameAndEntries(it)
            }
    }

    private fun mergeNameAndEntries(l: List<Pair<String, Map<String, List<FeedEntry>>>>): Map<String, List<Pair<String, FeedEntry>>> {
        val new: MutableMap<String, List<Pair<String, FeedEntry>>> = mutableMapOf()
        for ((name, map) in l) {
            for ((day, feedEntries: List<FeedEntry>) in map) {
                new[day] = new.getOrDefault(day, listOf()) + feedEntries.map { Pair(name, it) }
            }
        }
        return new
    }

    private fun getFormattedTime(entry: FeedEntry): String {
        return DateFormat.getDateInstance().format(entry.endTime.toDate())
    }

    /**
     * Givin a list of entries referencing the same task, in the same day, fuse them into one
     * feed-entry by adding the studytime and keeping the most recent end-timestamp
     */
    private fun fuseFeedEntries(entries: List<FeedEntry>): FeedEntry =
        entries.drop(1).fold(entries[0]) { accEntry, newEntry ->
            accEntry.copy(
                totalStudyTime = accEntry.totalStudyTime + newEntry.totalStudyTime,
                endTime = getMostRecent(accEntry.endTime, newEntry.endTime)
            )
        }

    private fun getMostRecent(t1: Timestamp, t2: Timestamp): Timestamp {
        return if (t1 < t2) t2 else t1
    }

    /**
     * Convert a sessionReport to a feedEntry. Fetch Task and Subject to get names
     */
    private suspend fun sessionToFeedEntry(sessionReport: SessionReport): FeedEntry {
        val subjectId: String = sessionReport.subjectId
        val taskId: String = sessionReport.taskId

        val task: Task = taskDAO.getTask(subjectId, taskId)
        val subject: Subject = subjectDAO.getSubject(subjectId)!!

        return makeFeedEntry(sessionReport, subject, task)
    }

    private fun makeFeedEntry(
        sessionReport: SessionReport,
        subject: Subject,
        task: Task
    ): FeedEntry {
        return FeedEntry(
            argb_color = subject.argb_color,
            subJectName = subject.name,
            taskName = task.name,
            taskId = task.id,
            subjectId = subject.id,
            totalStudyTime = sessionReport.studyTime,
            endTime = sessionReport.endTime,
            isArchived = task.archived || subject.archived
        )
    }

    /**
     * Convert a sessionReport to a feedEntry. Fetch Task and Subject to get names
     */
    private suspend fun sessionToFeedEntryFromUser(
        sessionReport: SessionReport,
        id: String
    ): FeedEntry {
        val subjectId: String = sessionReport.subjectId
        val taskId: String = sessionReport.taskId

        val task: Task = taskDAO.getTaskFromUser(subjectId, taskId, id)
        val subject: Subject = subjectDAO.getSubjectOfUSer(subjectId, id)

        return makeFeedEntry(sessionReport, subject, task)
    }
}