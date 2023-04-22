package be.ugent.sel.studeez.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.common.ext.isValidEmail
import be.ugent.sel.studeez.common.ext.isValidPassword
import be.ugent.sel.studeez.common.ext.passwordMatches
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.LOGIN_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.SIGN_UP_SCREEN
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import be.ugent.sel.studeez.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    private val userDAO: UserDAO,
    logService: LogService
    ) : StudeezViewModel(logService) {
    var uiState = mutableStateOf(SignUpUiState())
        private set

    private val username
        get() = uiState.value.username
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            accountDAO.signUpWithEmailAndPassword(email, password)
            accountDAO.signInWithEmailAndPassword(email, password)
            userDAO.save(username)
            openAndPopUp(HOME_SCREEN, SIGN_UP_SCREEN)
        }
    }

    fun onLoginClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(LOGIN_SCREEN, SIGN_UP_SCREEN)
    }
}