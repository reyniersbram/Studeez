package be.ugent.sel.studeez.screens.friends_feed

import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.domain.FeedDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.SessionDAO
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

@HiltViewModel
class FriendsFeedViewModel @Inject constructor(
    private val feedDAO: FeedDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun getFriendsSessions(): Flow<List<Pair<String, List<Pair<String, FeedEntry>>>>> {
        return feedDAO.getFriendsSessions().map { it.toList() }
    }


}

