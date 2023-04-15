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

    fun onHomeButtonClick(open: (String) -> Unit) {
        // TODO
    }

    fun onTimersClick(open: (String) -> Unit) {
        // TODO
    }

    fun onSettingsClick(open: (String) -> Unit) {
        // TODO
    }

    fun onLogoutClick(openAndPopup: (String, String) -> Unit) {
        launchCatching {
            accountDAO.signOut()
            openAndPopup(StudeezDestinations.LOGIN_SCREEN, StudeezDestinations.HOME_SCREEN)
        }
    }

    fun onAboutClick(open: (String) -> Unit) {
        // TODO
    }
}
