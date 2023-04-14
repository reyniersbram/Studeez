package be.ugent.sel.studeez.screens.drawer

import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun onLogoutClick(openAndPopup: (String, String) -> Unit) {
        launchCatching {
            accountDAO.signOut()
            openAndPopup(StudeezDestinations.LOGIN_SCREEN, StudeezDestinations.HOME_SCREEN)
        }
    }

    fun onHomeButtonClick(openAndPopup: (String, String) -> Unit) {
        // TODO
    }

    fun onTimersClick(openAndPopup: (String, String) -> Unit) {
        // TODO
    }

    fun onSettingsClick(openAndPopup: (String, String) -> Unit) {
        // TODO
    }

    fun onAboutClick(openAndPopup: (String, String) -> Unit) {
        // TODO
    }
}
