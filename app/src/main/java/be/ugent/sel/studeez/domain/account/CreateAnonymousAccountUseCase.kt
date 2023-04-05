package be.ugent.sel.studeez.domain.account

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CreateAnonymousAccountUseCase @Inject constructor(
    private val auth: FirebaseAuth,
) {
    suspend operator fun invoke() {
        auth.signInAnonymously().await()
    }
}