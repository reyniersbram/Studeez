package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.data.local.models.task.TaskDocument
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.TaskDAO
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireBaseTaskDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
) : TaskDAO {
    override fun getTasks(subject: Subject): Flow<List<Task>> {
        return selectedSubjectTasksCollection(subject.id)
            .nonArchived()
            .snapshots()
            .map { it.toObjects(Task::class.java) }
    }

    override suspend fun getTask(subjectId: String, taskId: String): Task {
        return selectedSubjectTasksCollection(subjectId).document(taskId).get().await().toObject()!!
    }

    override suspend fun getTaskCount(subject: Subject): Int {
        return selectedSubjectTasksCollection(subject.id)
            .nonArchived()
            .count()
            .get(AggregateSource.SERVER)
            .await()
            .count.toInt()
    }

    override suspend fun getCompletedTaskCount(subject: Subject): Int {
        return selectedSubjectTasksCollection(subject.id)
            .completed()
            .nonArchived()
            .count()
            .get(AggregateSource.SERVER)
            .await()
            .count.toInt()
    }

    override fun saveTask(newTask: Task) {
        selectedSubjectTasksCollection(newTask.subjectId).add(newTask)
    }

    override fun updateTask(newTask: Task) {
        selectedSubjectTasksCollection(newTask.subjectId).document(newTask.id).set(newTask)
    }

    override fun deleteTask(oldTask: Task) {
        selectedSubjectTasksCollection(oldTask.subjectId).document(oldTask.id).delete()
    }

    override fun toggleTaskCompleted(task: Task, completed: Boolean) {
        selectedSubjectTasksCollection(task.subjectId)
            .document(task.id)
//            .update(TaskDocument.completed, completed)
            .set(task.copy(completed = completed))
    }

    private fun selectedSubjectTasksCollection(subjectId: String): CollectionReference =
        firestore.collection(FireBaseCollections.USER_COLLECTION)
            .document(auth.currentUserId)
            .collection(FireBaseCollections.SUBJECT_COLLECTION)
            .document(subjectId)
            .collection(FireBaseCollections.TASK_COLLECTION)

    // Extend CollectionReference and Query with some filters

    private fun CollectionReference.nonArchived(): Query =
        this.whereEqualTo(TaskDocument.archived, false)

    private fun Query.nonArchived(): Query =
        this.whereEqualTo(TaskDocument.archived, false)

    private fun CollectionReference.completed(): Query =
        this.whereEqualTo(TaskDocument.completed, true)

    private fun Query.completed(): Query =
        this.whereEqualTo(TaskDocument.completed, true)

}