package be.ugent.sel.studeez.data.local.models.timer_info

import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer

class CustomTimerInfo(
    name: String,
    description: String,
    val studyTime: Int,
    id: String = ""
):  TimerInfo(id, name, description) {

    override fun getFunctionalTimer(): FunctionalTimer {
        return FunctionalCustomTimer(studyTime)
    }

    override fun asJson() : Map<String, Any> {
        return mapOf(
            "type" to "custom",
            "name" to name,
            "description" to description,
            "studyTime" to studyTime,
        )
    }

    override fun <T> accept(visitor: TimerInfoVisitor<T>): T {
        return visitor.visitCustomTimerInfo(this)
    }

}