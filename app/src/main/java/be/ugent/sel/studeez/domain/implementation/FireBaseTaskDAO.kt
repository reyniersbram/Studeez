package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.TaskDAO
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FireBaseTaskDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO,
) : TaskDAO {
    override fun getTasks(): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override fun saveTask(newTask: Task) {
        TODO("Not yet implemented")
    }

    override fun deleteTask(oldTask: Task) {
        TODO("Not yet implemented")
    }

}