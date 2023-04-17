package be.ugent.sel.studeez.data.local.models.timer_info

import com.google.firebase.firestore.DocumentId

/**
 * Timers uit de databank (remote config en firestore) worden als eerste stap omgezet naar dit type.
 */
data class TimerJson(
    val type: String = "",
    val name: String = "",
    val description: String = "",
    val studyTime: Int = 0,
    val breakTime: Int = 0,
    val repeats: Int = 0,
    @DocumentId val id: String = ""
)
