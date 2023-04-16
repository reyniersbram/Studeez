package be.ugent.sel.studeez.domain.implementation

import be.ugent.sel.studeez.data.local.models.timer_info.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonToTimerConverter {

    private val timerInfoMap: Map<String, TimerFactory> = mapOf(
        "endless" to TimerFactory { EndlessTimerInfo(it.name, it.description) },
        "custom" to TimerFactory { CustomTimerInfo(it.name, it.description, it.studyTime) },
        "break" to TimerFactory { PomodoroTimerInfo(
            it.name,
            it.description,
            it.studyTime,
            it.breakTime,
            it.repeats
        ) }
    )

    private fun getTimer(timerJson: TimerJson): TimerInfo{
        return timerInfoMap.getValue(timerJson.type).makeTimer(timerJson)
    }

    fun convertToTimerInfoList(a: List<TimerJson>): List<TimerInfo> {
        return a.map(this::getTimer)
    }

    fun jsonToTimerJsonList(json: String): List<TimerJson> {
        val type = object : TypeToken<List<TimerJson>>() {}.type
        return Gson().fromJson(json, type)
    }
}