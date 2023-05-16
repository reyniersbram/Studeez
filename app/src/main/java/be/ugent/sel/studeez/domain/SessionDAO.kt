package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import kotlinx.coroutines.flow.Flow

interface SessionDAO {

    fun getSessions(): Flow<List<SessionReport>>
    suspend fun getSessionsOfUser(userId: String): List<SessionReport>

    fun saveSession(newSessionReport: SessionReport)

    fun deleteSession(newTimer: TimerInfo)

}