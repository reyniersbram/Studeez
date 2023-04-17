package be.ugent.sel.studeez.domain

import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo

interface ConfigurationService {

    suspend fun fetchConfiguration(): Boolean

    fun getDefaultTimers(): List<TimerInfo>

}