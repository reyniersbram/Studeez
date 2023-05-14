package be.ugent.sel.studeez.screens.profile

import be.ugent.sel.studeez.domain.FriendshipDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDAO: UserDAO,
    private val friendshipDAO: FriendshipDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    suspend fun getUsername(): String {
        return userDAO.getLoggedInUser().username
    }

    suspend fun getBiography(): String {
        return userDAO.getLoggedInUser().biography
    }

    fun getAmountOfFriends(): Flow<Int> {
        return friendshipDAO.getFriendshipCount()
    }

    fun onEditProfileClick(open: (String) -> Unit) {
        open(StudeezDestinations.EDIT_PROFILE_SCREEN)
    }

    fun onViewFriendsClick(open: (String) -> Unit) {
        open(StudeezDestinations.FRIENDS_OVERVIEW_SCREEN)
    }

}