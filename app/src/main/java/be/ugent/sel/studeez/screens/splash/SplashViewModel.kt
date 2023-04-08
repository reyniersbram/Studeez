package be.ugent.sel.studeez.screens.splash

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
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
            // openAndPopUp( <homeScreen>, SPLASH_SCREEN)
        } else{
            // openAndPopUp(<login>, SPLASH_SCREEN)
        }
    }
}