package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.task.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectDAO {

    fun getSubjects(): Flow<List<Subject>>

    fun saveSubject(newSubject: Subject)

    fun deleteSubject(oldSubject: Subject)

    fun updateSubject(newSubject: Subject)

    suspend fun getTaskCount(subject: Subject): Int
    suspend fun getCompletedTaskCount(subject: Subject): Int
    fun getStudyTime(subject: Subject): Flow<Int>

    suspend fun getSubject(subjectId: String): Subject?
}