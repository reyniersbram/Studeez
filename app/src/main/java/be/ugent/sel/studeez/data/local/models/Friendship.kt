package be.ugent.sel.studeez.data.local.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Friendship(
    @DocumentId val id: String = "",
    val friendId: String = "",
    val friendsSince: Timestamp = Timestamp.now(),
    val accepted: Boolean = false
)