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

    fun isSelected(index: Int): Boolean {
        return index == selectedTab.selectedTab
    }

    fun onHomeClick(open: (String) -> Unit) {
        selectedTab.selectedTab = 0
        open(HOME_SCREEN)
    }

    fun onTasksClick(open: (String) -> Unit) {
        // TODO
        selectedTab.selectedTab = 1
    }

    fun onSessionsClick(open: (String) -> Unit) {
        // TODO
        selectedTab.selectedTab = 2
    }

    fun onProfileClick(open: (String) -> Unit) {
        selectedTab.selectedTab = 3
        open(PROFILE_SCREEN)
    }
}

@Singleton
class SelectedTabState @Inject constructor() {
    var selectedTab: Int = 0
}