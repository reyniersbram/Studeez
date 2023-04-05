package be.ugent.sel.studeez.domain.account

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val auth: FirebaseAuth,
    private val createAnonymousAccountUseCase: CreateAnonymousAccountUseCase,
) {
    suspend operator fun invoke() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()

        createAnonymousAccountUseCase()
    }
}