package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.TaskDAO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FireBaseTaskDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
) : TaskDAO {
    override fun getTasks(subject: Subject): Flow<List<Task>> {
        return flowOf(
            listOf(
                Task(
                    name = "Test Task",
                    completed = false,
                ),
                Task(
                    name = "Test Task 2",
                    completed = true,
                )
            )
        )
    }

    override fun saveTask(newTask: Task) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(oldTask: Task) {
        TODO("Not yet implemented")
    }

}