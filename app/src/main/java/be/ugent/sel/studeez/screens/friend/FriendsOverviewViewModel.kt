package be.ugent.sel.studeez.screens.friend

import be.ugent.sel.studeez.data.local.models.Friendship
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.domain.FriendshipDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

@HiltViewModel
class FriendsOverviewViewModel @Inject constructor(
    private val userDAO: UserDAO,
    private val friendshipDAO: FriendshipDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun getAllFriends(): Flow<List<Pair<User, Friendship>>> {
        return friendshipDAO.getAllFriendships()
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

}