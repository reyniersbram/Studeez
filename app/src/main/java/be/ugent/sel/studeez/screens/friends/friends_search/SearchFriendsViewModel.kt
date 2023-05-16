package be.ugent.sel.studeez.screens.friends.friends_search

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.SelectedUserId
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.data.remote.FirebaseUser
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchFriendsViewModel @Inject constructor(
    private val userDAO: UserDAO,
    private val selectedProfileState: SelectedUserId,
    logService: LogService
): StudeezViewModel(logService) {

    var uiState = mutableStateOf(SearchFriendUiState())
        private set

    fun onQueryStringChange(newValue: String) {
        uiState.value = uiState.value.copy(
            queryString = newValue
        )
        uiState.value = uiState.value.copy(
            searchResults = userDAO.getUsersWithQuery(
                fieldName = FirebaseUser.USERNAME,
                value = uiState.value.queryString
            )
        )
    }

    fun getUsersWithUsername(
        value: String
    ): Flow<List<User>> {
        return userDAO.getUsersWithQuery(
            fieldName = FirebaseUser.USERNAME,
            value = value
        )
    }

    /**
     * Get all users, except for the current user.
     */
    fun getAllUsers(): Flow<List<User>> {
        return userDAO.getAllUsers()
            .map { users ->
                users.filter { user ->
                    user.id != userDAO.getCurrentUserId()
                }
            }
    }

    fun goToProfile(
        userId: String,
        open: (String) -> Unit
    ) {
        selectedProfileState.value = userId
        open(StudeezDestinations.PUBLIC_PROFILE_SCREEN)
    }
}