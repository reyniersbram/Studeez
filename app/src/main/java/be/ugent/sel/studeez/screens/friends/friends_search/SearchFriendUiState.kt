package be.ugent.sel.studeez.screens.friends.friends_search

import be.ugent.sel.studeez.data.local.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class SearchFriendUiState(
    val queryString: String = "",
    val searchResults: Flow<List<User>> = emptyFlow()
)