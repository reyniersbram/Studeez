package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerJson
import kotlinx.coroutines.flow.Flow

interface TimerDAO {

    fun getUserTimers(): Flow<List<TimerInfo>>

    fun getAllTimers(): Flow<List<TimerInfo>>

    fun saveTimer(newTimer: TimerInfo)

    fun updateTimer(timerInfo: TimerInfo)

    fun deleteTimer(timerInfo: TimerInfo)

}