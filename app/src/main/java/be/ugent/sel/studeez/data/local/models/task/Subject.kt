package be.ugent.sel.studeez.data.local.models.task

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude

data class Subject(
    @DocumentId val id: String = "",
    val name: String = "",
    val argb_color: Long = 0,
    @get:Exclude @set:Exclude
    var taskCount: Int = 0,
    @get:Exclude @set:Exclude
    var taskCompletedCount: Int = 0,
    @get:Exclude @set:Exclude
    var time: Int = 0,
)
