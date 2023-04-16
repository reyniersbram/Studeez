package be.ugent.sel.studeez.domain.implementation

import androidx.compose.runtime.rememberCoroutineScope
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.UserDAO
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class FirebaseUserDAO @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountDAO
    ) : UserDAO {

    override suspend fun getUsername(): String? {
        return currentUserDocument().get().await().getString("username")
    }

    override suspend fun save(newUsername: String) {
        currentUserDocument().set(mapOf("username" to newUsername))
    }

    private fun currentUserDocument(): DocumentReference =
        firestore.collection(USER_COLLECTION).document(auth.currentUserId)

    companion object {
        private const val USER_COLLECTION = "users"
    }
}