package be.ugent.sel.studeez.screens.home

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.LOGIN_SCREEN
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun onStartSessionClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(StudeezDestinations.SESSION_SCREEN, StudeezDestinations.HOME_SCREEN)
    }

    fun onLogoutClick(openAndPopup: (String, String) -> Unit) {
        launchCatching {
            accountDAO.signOut()
            openAndPopup(LOGIN_SCREEN, HOME_SCREEN)
        }
    }
}