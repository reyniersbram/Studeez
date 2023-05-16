package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.data.local.models.task.TaskDocument
import be.ugent.sel.studeez.domain.AccountDAO
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

class FirebaseTaskDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
) : TaskDAO {
    override fun getTasks(subject: Subject): Flow<List<Task>> {
        return selectedSubjectTasksCollection(subject.id)
            .taskNotArchived()
            .snapshots()
            .map { it.toObjects(Task::class.java) }
    }

    override suspend fun getTask(subjectId: String, taskId: String): Task {
        return selectedSubjectTasksCollection(subjectId).document(taskId).get().await().toObject()!!
    }

    override suspend fun getTaskFromUser(subjectId: String, taskId: String, userId: String): Task {
        return selectedSubjectTasksCollection(subjectId, userId)
            .document(taskId)
            .get()
            .await().toObject(Task::class.java)!!
    }

    override fun saveTask(newTask: Task) {
        selectedSubjectTasksCollection(newTask.subjectId).add(newTask)
    }

    override fun updateTask(newTask: Task) {
        selectedSubjectTasksCollection(newTask.subjectId)
            .document(newTask.id)
            .set(newTask)
    }

    override fun deleteTask(oldTask: Task) {
        selectedSubjectTasksCollection(oldTask.subjectId).document(oldTask.id).delete()
    }

    private fun selectedSubjectTasksCollection(subjectId: String, id: String = auth.currentUserId): CollectionReference =
        firestore.collection(FirebaseCollections.USER_COLLECTION)
            .document(id)
            .collection(FirebaseCollections.SUBJECT_COLLECTION)
            .document(subjectId)
            .collection(FirebaseCollections.TASK_COLLECTION)
}

// Extend CollectionReference and Query with some filters

fun CollectionReference.taskNotArchived(): Query =
    this.whereEqualTo(TaskDocument.archived, false)

fun Query.taskNotArchived(): Query =
    this.whereEqualTo(TaskDocument.archived, false)

fun CollectionReference.taskNotCompleted(): Query =
    this.whereEqualTo(TaskDocument.completed, true)

fun Query.taskNotCompleted(): Query =
    this.whereEqualTo(TaskDocument.completed, true)
