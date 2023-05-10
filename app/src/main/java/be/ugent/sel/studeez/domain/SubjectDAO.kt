package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.task.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectDAO {

    fun getSubjects(): Flow<List<Subject>>

    fun saveSubject(newSubject: Subject)

    fun deleteSubject(oldSubject: Subject)

    fun updateSubject(newSubject: Subject)

    fun getTaskCount(subject: Subject): Flow<Int>
    fun getCompletedTaskCount(subject: Subject): Flow<Int>
    fun getStudyTime(subject: Subject): Flow<Int>

    suspend fun getSubject(subjectId: String): Subject?
}