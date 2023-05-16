package be.ugent.sel.studeez.common.composable.drawer

import android.content.Context
import android.content.Intent
import android.net.Uri
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.LOGIN_SCREEN
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val REPO_URL: String = "https://github.ugent.be/SELab1/project2023-groep14/"

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun onHomeButtonClick(open: (String) -> Unit) {
        open(HOME_SCREEN)
    }

    fun onTimersClick(openAndPopup: (String) -> Unit) {
        openAndPopup(StudeezDestinations.TIMER_SCREEN)
    }

    fun onSettingsClick(open: (String) -> Unit) {
        open(StudeezDestinations.SETTINGS_SCREEN)
    }

    fun onLogoutClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            accountDAO.signOut()
            openAndPopUp(LOGIN_SCREEN, HOME_SCREEN)
        }
    }

    fun onAboutClick(open: (String) -> Unit, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(REPO_URL))
        context.startActivity(intent)
    }
}
