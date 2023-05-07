package be.ugent.sel.studeez.data.local.models

data class FeedEntry(
    val argb_color: Long = 0,
    val subJectName: String = "",
    val taskName: String = "",
    val taskId: String = "", // Name of task is not unique
    val subjectId: String = "",
    val totalStudyTime: Int = 0
)
