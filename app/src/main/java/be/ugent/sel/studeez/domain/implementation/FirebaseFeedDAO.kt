package be.ugent.sel.studeez.domain.implementation

import android.icu.text.DateFormat
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.FeedDAO
import be.ugent.sel.studeez.domain.SessionDAO
import be.ugent.sel.studeez.domain.TaskDAO
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FirebaseFeedDAO @Inject constructor(
    private val sessionDAO: SessionDAO,
    private val taskDAO: TaskDAO,
    private val subjectDAO: FireBaseSubjectDAO
) : FeedDAO {

    /**
     *  Return a map as with key the day and value a list of feedentries for that day.
     */
    override fun getFeedEntries(): Flow<Map<String, List<FeedEntry>>> {
        return sessionDAO.getSessions().map {sessionReports ->
            sessionReports
                .map { sessionReport ->  sessionToFeedEntry(sessionReport) }
                .sortedByDescending { it.endTime }
                .groupBy { getFormattedTime(it) }
                .mapValues { (_, entries) ->
                    entries
                    .groupBy { it.taskId }
                    .map { fuseFeedEntries(it.component2()) }
                }
        }
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

        return FeedEntry(
            argb_color = subject.argb_color,
            subJectName = subject.name,
            taskName = task.name,
            taskId = task.id,
            subjectId = subject.id,
            totalStudyTime = sessionReport.studyTime,
            endTime = sessionReport.endTime,
            isArchived = task.completed
        )
    }
}