package be.ugent.sel.studeez.common.composable.navbar

import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations.FRIENDS_FEED
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.PROFILE_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.SEARCH_FRIENDS_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.SELECT_SUBJECT
import be.ugent.sel.studeez.navigation.StudeezDestinations.SESSIONS_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.SUBJECT_SCREEN
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import be.ugent.sel.studeez.R.string as AppText

@HiltViewModel
class NavigationBarViewModel @Inject constructor(
    logService: LogService
) : StudeezViewModel(logService) {

    fun onHomeClick(open: (String) -> Unit) {
        open(HOME_SCREEN)
    }

    fun onTasksClick(open: (String) -> Unit) {
        open(SUBJECT_SCREEN)
    }

    fun onSessionsClick(open: (String) -> Unit) {
        open(FRIENDS_FEED)
    }

    fun onProfileClick(open: (String) -> Unit) {
        open(PROFILE_SCREEN)
    }

    fun onAddTaskClick(open: (String) -> Unit) {
        open(SELECT_SUBJECT)
    }

    fun onAddFriendClick(open: (String) -> Unit) {
        open(SEARCH_FRIENDS_SCREEN)
    }

    fun onAddSessionClick(open: (String) -> Unit) {
        // TODO open(CREATE_SESSION_SCREEN)
        SnackbarManager.showMessage(AppText.create_session_not_possible_yet) // TODO Remove
    }
}