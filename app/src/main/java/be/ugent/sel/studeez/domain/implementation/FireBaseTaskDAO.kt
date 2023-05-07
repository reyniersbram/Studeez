package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.data.local.models.task.TaskDocument
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.TaskDAO
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireBaseTaskDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
) : TaskDAO {
    override fun getTasks(subject: Subject): Flow<List<Task>> {
        return selectedSubjectTasksCollection(subject.id)
            .snapshots()
            .map { it.toObjects(Task::class.java) }
    }

    override suspend fun getTask(subjectId: String, taskId: String): Task {
        return selectedSubjectTasksCollection(subjectId).document(taskId).get().await().toObject()!!
    }

    override fun saveTask(newTask: Task) {
        selectedSubjectTasksCollection(newTask.subjectId).add(newTask)
    }

    override fun updateTask(newTask: Task) {
        selectedSubjectTasksCollection(newTask.id).document(newTask.id).set(newTask)
    }

    override fun deleteTask(oldTask: Task) {
        selectedSubjectTasksCollection(oldTask.subjectId).document(oldTask.id).delete()
    }

    override fun toggleTaskCompleted(task: Task, completed: Boolean) {
        selectedSubjectTasksCollection(task.subjectId)
            .document(task.id)
            .update(TaskDocument.completed, completed)
    }

    private fun selectedSubjectTasksCollection(subjectId: String): CollectionReference =
        firestore.collection(FireBaseCollections.USER_COLLECTION)
            .document(auth.currentUserId)
            .collection(FireBaseCollections.SUBJECT_COLLECTION)
            .document(subjectId)
            .collection(FireBaseCollections.TASK_COLLECTION)
}