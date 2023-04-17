package be.ugent.sel.studeez.data.local.models.timer_info

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer

class BreakTimerInfo(
    name: String,
    description: String,
    private val studyTime: Int,
    private val breakTime: Int,
    private val repeats: Int,
    id: String = ""
):  TimerInfo(id, name, description) {


    override fun getFunctionalTimer(): FunctionalTimer {
        return FunctionalPomodoroTimer(studyTime, breakTime, repeats)
    }

    override fun asJson() : Map<String, Any> {
        return mapOf(
            "type" to "break",
            "name" to name,
            "description" to description,
            "studyTime" to studyTime,
            "breakTime" to breakTime,
            "repeats" to repeats,
        )
    }

}