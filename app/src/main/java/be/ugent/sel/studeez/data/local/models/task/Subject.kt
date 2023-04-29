package be.ugent.sel.studeez.data.local.models.task

import com.google.firebase.firestore.DocumentId

data class Subject(
    @DocumentId val id: String = "",
    val name: String = "",
    val tasks: List<Task> = mutableListOf(),
    val time: Int = 0,
    val color: Int = 0,
)