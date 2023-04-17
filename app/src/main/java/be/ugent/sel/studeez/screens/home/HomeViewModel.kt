package be.ugent.sel.studeez.screens.home

import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.LOGIN_SCREEN
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun onStartSessionClick(openAndPopUp: (String) -> Unit) {
        openAndPopUp(StudeezDestinations.SESSION_SCREEN)
    }

    fun onLogoutClick(openAndPopup: (String, String) -> Unit) {
        launchCatching {
            accountDAO.signOut()
            openAndPopup(LOGIN_SCREEN, HOME_SCREEN)
        }
    }
}