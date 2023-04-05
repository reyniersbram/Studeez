package be.ugent.sel.studeez.domain.account

import be.ugent.sel.studeez.domain.trace
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LinkAccountUseCase @Inject constructor(
    private val auth: FirebaseAuth,
) {

    suspend operator fun invoke(email: String, password: String): Unit =
        trace(LINK_ACCOUNT_TRACE) {
            val credential = EmailAuthProvider.getCredential(email, password)
            auth.currentUser!!.linkWithCredential(credential).await()
        }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}