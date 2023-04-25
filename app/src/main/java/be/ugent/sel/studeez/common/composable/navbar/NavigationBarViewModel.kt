package be.ugent.sel.studeez.common.composable.navbar

import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.PROFILE_SCREEN
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class NavigationBarViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    var selectedTab: SelectedTabState,
    logService: LogService
) : StudeezViewModel(logService) {

    fun isSelected(screen: String): Boolean {
        return screen == selectedTab.selectedTab
    }

    fun onHomeClick(open: (String) -> Unit) {
        selectedTab.selectedTab = HOME_SCREEN
        open(HOME_SCREEN)
    }

    fun onTasksClick(open: (String) -> Unit) {
        // TODO
        // selectedTab.selectedTab = TASKS_SCREEN
    }

    fun onSessionsClick(open: (String) -> Unit) {
        // TODO
        // selectedTab.selectedTab = SESSIONS_SCREEN
    }

    fun onProfileClick(open: (String) -> Unit) {
        selectedTab.selectedTab = PROFILE_SCREEN
        open(PROFILE_SCREEN)
    }
}

@Singleton
class SelectedTabState @Inject constructor() {
    var selectedTab: String = HOME_SCREEN
}