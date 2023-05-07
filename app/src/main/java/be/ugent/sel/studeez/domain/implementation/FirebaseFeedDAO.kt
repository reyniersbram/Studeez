package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.FeedDAO
import be.ugent.sel.studeez.domain.SessionDAO
import be.ugent.sel.studeez.domain.TaskDAO
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FirebaseFeedDAO @Inject constructor(
    private val sessionDAO: SessionDAO,
    private val taskDAO: TaskDAO,
    private val subjectDAO: FireBaseSubjectDAO
) : FeedDAO {

    override fun getFeedEntries(): Flow<List<FeedEntry>> {
        return sessionDAO.getSessions().map {sessionReports ->
            sessionReports
                .map { sessionReport ->  sessionToFeedEntry(sessionReport) }
                .groupBy { it.taskId }
                .map { fuseFeedEntries(it.component2()) }
        }
    }

    private fun fuseFeedEntries(entries: List<FeedEntry>): FeedEntry =
        entries.fold(entries[0]) { accEntry, newEntry ->
            val newStudyTime = accEntry.totalStudyTime + newEntry.totalStudyTime
            accEntry.copy(totalStudyTime = newStudyTime)
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
            totalStudyTime = sessionReport.studyTime
        )
    }
}