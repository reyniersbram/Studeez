package be.ugent.sel.studeez.screens.profile.public_profile

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.domain.FriendshipDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.selects.select
import javax.inject.Inject

@HiltViewModel
class PublicProfileViewModel @Inject constructor(
    private val userDAO: UserDAO,
    private val friendshipDAO: FriendshipDAO,
    selectedProfileState: SelectedProfileState,
    logService: LogService
): StudeezViewModel(logService) {

    val uiState = mutableStateOf(
        PublicProfileUiState(
            userId = selectedProfileState.selectedUserId
        )
    )

    fun getUserDetails(
        userId: String
    ): Flow<User> {
        uiState.value = uiState.value.copy(
            userId = userId
        )
        return userDAO.getUserDetails(
            userId = uiState.value.userId
        )
    }

    fun getAmountOfFriends(
        userId: String
    ): Flow<Int> {
        return friendshipDAO.getFriendshipCount(
            userId = userId
        )
    }

    fun onViewFriendsClick(
        open: (String) -> Unit
    ) {
        open(StudeezDestinations.FRIENDS_OVERVIEW_SCREEN)
    }

    fun sendFriendRequest(
        userId: String
    ): Boolean {
        return friendshipDAO.sendFriendshipRequest(userId)
    }

}