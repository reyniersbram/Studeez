package be.ugent.sel.studeez.data.local.models.task

import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId val id: String = "",
    val name: String = "",
    val completed: Boolean = false,
)
