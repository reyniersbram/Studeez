package be.ugent.sel.studeez.screens.friends_feed

import be.ugent.sel.studeez.data.local.models.FeedEntry

sealed interface FriendsFeedUiState {
    object Loading : FriendsFeedUiState
    data class Succes(
        val friendSessions: List<Pair<String, List<Pair<String, FeedEntry>>>>,
    ) : FriendsFeedUiState
}