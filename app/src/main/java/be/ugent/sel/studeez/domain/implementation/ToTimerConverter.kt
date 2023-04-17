package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.timer_info.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Used by ConfigurationService and TimerDAO.
 *
 * ConfigurationService: configuration is fetched as a JSON-string,
 * which is converted into a TimerJson, and converted here into the correct TimerInfo
 *
 * timerDAO: Timers are being fetched directly to TinerJson and convertes into the correct timerInfo
 */
class ToTimerConverter {

    fun interface TimerFactory {
        fun makeTimer(map: TimerJson) : TimerInfo
    }

    private val timerInfoMap: Map<TimerType, TimerFactory> = mapOf(
        TimerType.ENDLESS to TimerFactory { EndlessTimerInfo(
            it.name,
            it.description,
            it.id
        ) },
        TimerType.CUSTOM to TimerFactory { CustomTimerInfo(
            it.name,
            it.description,
            it.studyTime,
            it.id
        ) },
        TimerType.BREAK to TimerFactory { BreakTimerInfo(
            it.name,
            it.description,
            it.studyTime,
            it.breakTime,
            it.repeats,
            it.id
        ) }
    )

    private fun getTimer(timerJson: TimerJson): TimerInfo{
        val type: TimerType = TimerType.valueOf(timerJson.type.uppercase())
        return timerInfoMap.getValue(type).makeTimer(timerJson)
    }

    fun convertToTimerInfoList(timerJsonList: List<TimerJson>): List<TimerInfo> {
        return timerJsonList.map(this::getTimer)
    }

    fun jsonToTimerJsonList(json: String): List<TimerJson> {
        val type = object : TypeToken<List<TimerJson>>() {}.type
        return Gson().fromJson(json, type)
    }
}