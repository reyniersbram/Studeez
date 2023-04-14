package be.ugent.sel.studeez.screens.navbar

import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationBarViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun onHomeClick(openAndPopup: (String, String) -> Unit) {
        // TODO
    }

    fun onTasksClick(openAndPopup: (String, String) -> Unit) {
        // TODO
    }

    fun onSessionsClick(openAndPopup: (String, String) -> Unit) {
        // TODO
    }

    fun onProfileClick(openAndPopup: (String, String) -> Unit) {
        // TODO
    }
}