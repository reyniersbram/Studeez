package be.ugent.sel.studeez.data.local.models

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
    val username: String = "",
    val biography: String = ""
)
