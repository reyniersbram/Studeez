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
                    .sortedByDescending { it.endTime }
                }
        }
    }

    private fun getFormattedTime(entry: FeedEntry): String {
        return DateFormat.getDateInstance().format(entry.endTime.toDate())
    }

    private fun fuseFeedEntries(entries: List<FeedEntry>): FeedEntry =
        entries.fold(entries[0]) { accEntry, newEntry ->
            accEntry.copy(
                totalStudyTime = accEntry.totalStudyTime + newEntry.totalStudyTime,
                endTime = getMostRecent(accEntry.endTime, newEntry.endTime)
            )
        }

    private fun getMostRecent(t1: Timestamp, t2: Timestamp): Timestamp {
        return if (t1 < t2) t2 else t1
    }


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
            endTime = sessionReport.endTime
        )
    }
}