package be.ugent.sel.studeez.data.local.models.task

import com.google.firebase.firestore.DocumentId

data class Subject(
    @DocumentId val id: String = "",
    val name: String = "",
    val tasks: List<Task> = mutableListOf(),
    val time: Int = 0,
    val argb_color: Long = 0,
) {
//    fun getColor(): Color = Color(ARGB_color)
}