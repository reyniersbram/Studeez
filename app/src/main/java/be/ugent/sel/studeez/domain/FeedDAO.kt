package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.FeedEntry
import kotlinx.coroutines.flow.Flow

interface FeedDAO {

    fun getFeedEntries(): Flow<Map<String, List<FeedEntry>>>

    suspend fun getFeedEntriesFromUser(id: String): Map<String, List<FeedEntry>>

    fun getFriendsSessions(): Flow<Map<String, List<Pair<String, FeedEntry>>>>
}