package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import kotlinx.coroutines.flow.Flow

interface SessionDAO {

    fun getSessions(): Flow<List<SessionReport>>
    suspend fun getSessionsOfUser(userId: String): List<SessionReport>

    /**
     * Return a list of pairs, containing the username and all the studysessions of that user.
     */
    fun getFriendsSessions(): Flow<List<Pair<String,List<SessionReport>>>>

    fun saveSession(newSessionReport: SessionReport)

    fun deleteSession(newTimer: TimerInfo)

}