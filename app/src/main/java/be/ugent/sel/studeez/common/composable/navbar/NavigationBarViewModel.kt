package be.ugent.sel.studeez.common.composable.navbar

import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.PROFILE_SCREEN
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationBarViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun onHomeClick(open: (String) -> Unit) {
        open(HOME_SCREEN)
    }

    fun onTasksClick(open: (String) -> Unit) {
        // TODO
    }

    fun onSessionsClick(open: (String) -> Unit) {
        // TODO
    }

    fun onProfileClick(open: (String) -> Unit) {
        open(PROFILE_SCREEN)
    }
}