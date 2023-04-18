package be.ugent.sel.studeez.data.local.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class SessionReport(
    @DocumentId val id: String = "",
    val studyTime: Int = 0,
    val endTime: Timestamp = Timestamp(0, 0)
    )