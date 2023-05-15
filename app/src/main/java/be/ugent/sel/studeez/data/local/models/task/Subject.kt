package be.ugent.sel.studeez.data.local.models.task

import com.google.firebase.firestore.DocumentId

data class Subject(
    @DocumentId val id: String = "",
    val name: String = "",
    val argb_color: Long = 0,
    var archived: Boolean = false,
)

object SubjectDocument {
    const val id = "id"
    const val name = "name"
    const val archived = "archived"
    const val argb_color = "argb_color"
}