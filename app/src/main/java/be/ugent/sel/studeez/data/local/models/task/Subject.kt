package be.ugent.sel.studeez.data.local.models.task

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude

data class Subject(
    @DocumentId val id: String = "",
    val name: String = "",
    val argb_color: Long = 0,
    var archived: Boolean = false,
    @get:Exclude @set:Exclude
    var taskCount: Int = 0,
    @get:Exclude @set:Exclude
    var taskCompletedCount: Int = 0,
)

object SubjectDocument {
    const val id = "id"
    const val name = "name"
    const val archived = "archived"
    const val argb_color = "argb_color"
}