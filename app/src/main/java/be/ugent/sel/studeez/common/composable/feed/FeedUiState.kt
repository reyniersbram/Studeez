package be.ugent.sel.studeez.common.composable.feed

import be.ugent.sel.studeez.data.local.models.FeedEntry

sealed interface FeedUiState {
    object Loading : FeedUiState
    data class Succes(val feedEntries: Map<String, List<FeedEntry>>) : FeedUiState
}