package be.ugent.sel.studeez.screens.profile.public_profile

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.data.SelectedUserId
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.domain.FriendshipDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import be.ugent.sel.studeez.R.string as AppText

@HiltViewModel
class PublicProfileViewModel @Inject constructor(
    private val userDAO: UserDAO,
    private val friendshipDAO: FriendshipDAO,
    selectedUserIdState: SelectedUserId,
    logService: LogService
): StudeezViewModel(logService) {

    val uiState = mutableStateOf(
        PublicProfileUiState(
            userId = selectedUserIdState.value
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
    ) {
        friendshipDAO.sendFriendshipRequest(userId)
    }

}