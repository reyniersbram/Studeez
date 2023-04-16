package be.ugent.sel.studeez.data.local.models.timer_info

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer

class EndlessTimerInfo(
    name: String,
    description: String,
    id: String = ""
):  TimerInfo(id, name, description) {


    override fun getFunctionalTimer(): FunctionalTimer {
        return FunctionalEndlessTimer()
    }

    override fun asJson() : Map<String, Any> {
        return mapOf(
            "type" to "endless",
            "name" to name,
            "description" to description
        )
    }

}