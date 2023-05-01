package be.ugent.sel.studeez.common.composable.navbar

import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations.CREATE_SESSION_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.CREATE_TASK_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.PROFILE_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.SEARCH_FRIENDS_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.SESSIONS_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.TASKS_SCREEN
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
        open(TASKS_SCREEN)
    }

    fun onSessionsClick(open: (String) -> Unit) {
        open(SESSIONS_SCREEN)
    }

    fun onProfileClick(open: (String) -> Unit) {
        open(PROFILE_SCREEN)
    }

    fun onAddTaskClick(open: (String) -> Unit) {
        open(CREATE_TASK_SCREEN)
    }

    fun onAddFriendClick(open: (String) -> Unit) {
        open(SEARCH_FRIENDS_SCREEN)
    }

    fun onAddSessionClick(open: (String) -> Unit) {
        open(CREATE_SESSION_SCREEN)
    }
}