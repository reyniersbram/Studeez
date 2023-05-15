package be.ugent.sel.studeez.domain.implementation

import android.util.Log
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.SubjectDocument
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.data.local.models.task.TaskDocument
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.SubjectDAO
import be.ugent.sel.studeez.domain.TaskDAO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.count

class FirebaseSubjectDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
    private val taskDAO: TaskDAO,
) : SubjectDAO {
    override fun getSubjects(): Flow<List<Subject>> {
        return currentUserSubjectsCollection()
            .subjectNotArchived()
            .snapshots()
            .map { it.toObjects(Subject::class.java) }
    }

    override suspend fun getSubject(subjectId: String): Subject? {
        return currentUserSubjectsCollection().document(subjectId).get().await().toObject()
    }

    override fun saveSubject(newSubject: Subject) {
        currentUserSubjectsCollection().add(newSubject)
    }

    override fun deleteSubject(oldSubject: Subject) {
        currentUserSubjectsCollection().document(oldSubject.id).delete()
    }

    override fun updateSubject(newSubject: Subject) {
        currentUserSubjectsCollection().document(newSubject.id).set(newSubject)
    }

    override suspend fun archiveSubject(subject: Subject) {
        currentUserSubjectsCollection().document(subject.id).update(SubjectDocument.archived, true)
        currentUserSubjectsCollection().document(subject.id)
            .collection(FirebaseCollections.TASK_COLLECTION)
            .taskNotArchived()
            .get().await()
            .documents
            .forEach {
                it.reference.update(TaskDocument.archived, true)
            }
    }

    override fun getTaskCount(subject: Subject): Flow<Int> {
        return taskDAO.getTasks(subject)
            .map(List<Task>::count)
    }

    override fun getCompletedTaskCount(subject: Subject): Flow<Int> {
        return taskDAO.getTasks(subject)
            .map { tasks -> tasks.count { it.completed && !it.archived } }
    }

    override fun getStudyTime(subject: Subject): Flow<Int> {
        return taskDAO.getTasks(subject)
            .map { tasks -> tasks.sumOf { it.time } }
    }

    private fun currentUserSubjectsCollection(): CollectionReference =
        firestore.collection(FirebaseCollections.USER_COLLECTION)
            .document(auth.currentUserId)
            .collection(FirebaseCollections.SUBJECT_COLLECTION)

    private fun subjectTasksCollection(subject: Subject): CollectionReference =
        firestore.collection(FirebaseCollections.USER_COLLECTION)
            .document(auth.currentUserId)
            .collection(FirebaseCollections.SUBJECT_COLLECTION)
            .document(subject.id)
            .collection(FirebaseCollections.TASK_COLLECTION)

    fun CollectionReference.subjectNotArchived(): Query =
        this.whereEqualTo(SubjectDocument.archived, false)

    fun Query.subjectNotArchived(): Query =
        this.whereEqualTo(SubjectDocument.archived, false)
}
