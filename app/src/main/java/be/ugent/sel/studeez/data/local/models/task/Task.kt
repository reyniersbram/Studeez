package be.ugent.sel.studeez.data.local.models.task

import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId val id: String = "",
    val name: String = "",
    var completed: Boolean = false,
    val time: Int = 0,
    val subjectId: String = "",
    var archived: Boolean = false,
)

object TaskDocument {
    const val id = "id"
    const val name = "name"
    const val completed = "completed"
    const val time = "time"
    const val subjectId = "subjectId"
    const val archived = "archived"
}
