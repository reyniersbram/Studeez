package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.FeedEntry
import kotlinx.coroutines.flow.Flow

interface FeedDAO {

    fun getFeedEntries(): Flow<Map<String, List<FeedEntry>>>

}