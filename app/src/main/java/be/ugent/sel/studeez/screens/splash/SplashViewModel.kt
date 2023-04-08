package be.ugent.sel.studeez.screens.splash

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    logService: LogService
) : StudeezViewModel(logService) {
    val showError = mutableStateOf(false)

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        showError.value = false
        if (accountDAO.hasUser) {
            // TODO this should go to the home page
            openAndPopUp(StudeezDestinations.SIGN_UP_SCREEN, StudeezDestinations.SPLASH_SCREEN)
        } else{
            openAndPopUp(StudeezDestinations.SIGN_UP_SCREEN, StudeezDestinations.SPLASH_SCREEN)
        }
    }
}