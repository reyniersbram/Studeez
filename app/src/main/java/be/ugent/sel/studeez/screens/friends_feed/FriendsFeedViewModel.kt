package be.ugent.sel.studeez.screens.friends_feed

import androidx.lifecycle.viewModelScope
import be.ugent.sel.studeez.domain.FeedDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FriendsFeedViewModel @Inject constructor(
    private val feedDAO: FeedDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    val uiState: StateFlow<FriendsFeedUiState> = feedDAO.getFriendsSessions()
        .map { it.toList() }
        .map { FriendsFeedUiState.Succes(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = FriendsFeedUiState.Loading,
            started = SharingStarted.Eagerly,
        )
}

