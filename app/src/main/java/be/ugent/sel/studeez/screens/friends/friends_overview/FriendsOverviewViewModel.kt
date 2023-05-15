package be.ugent.sel.studeez.screens.friends.friends_overview

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.local.models.Friendship
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.domain.FriendshipDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import be.ugent.sel.studeez.screens.profile.public_profile.SelectedProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

@HiltViewModel
class FriendsOverviewViewModel @Inject constructor(
    private val userDAO: UserDAO,
    private val friendshipDAO: FriendshipDAO,
    private val selectedProfileState: SelectedProfileState,
    logService: LogService
) : StudeezViewModel(logService) {

    var uiState = mutableStateOf(FriendsOverviewUiState(
        userId = selectedProfileState.selectedUserId
    ))
        private set

    fun getAllFriends(): Flow<List<Pair<User, Friendship>>> {
        return friendshipDAO.getAllFriendships(
                userId = uiState.value.userId
            )
            .flatMapConcat { friendships ->
                val userFlows = friendships.map { friendship ->
                    userDAO.getUserDetails(friendship.friendId)
                }
                combine(userFlows) { users ->
                    friendships.zip(users) { friendship, user ->
                        Pair(user, friendship)
                    }
                }
            }
    }

    fun searchFriends(open: (String) -> Unit) {
        open(StudeezDestinations.SEARCH_FRIENDS_SCREEN)
    }

    fun onQueryStringChange(newValue: String) {
        uiState.value = uiState.value.copy(
            queryString = newValue
        )
    }

    fun onSubmit(open: (String) -> Unit) {
        val query = uiState.value.queryString // TODO Pass as argument
        open(StudeezDestinations.SEARCH_FRIENDS_SCREEN)
    }

    fun viewProfile(
        userId: String,
        open: (String) -> Unit
    ) {
        selectedProfileState.selectedUserId = userId
        open(StudeezDestinations.PUBLIC_PROFILE_SCREEN)
    }

    fun removeFriend(
        friendship: Friendship
    ) {
        friendshipDAO.removeFriendship(
            friendship = friendship
        )
    }

}