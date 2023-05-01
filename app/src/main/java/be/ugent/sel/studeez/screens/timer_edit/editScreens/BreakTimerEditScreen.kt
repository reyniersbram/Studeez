package be.ugent.sel.studeez.screens.timer_edit.editScreens

import androidx.compose.runtime.*
import be.ugent.sel.studeez.common.composable.TimePickerButton
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.data.local.models.timer_functional.Time
import be.ugent.sel.studeez.data.local.models.timer_info.PomodoroTimerInfo

class BreakTimerEditScreen(
    private val breakTimerInfo: PomodoroTimerInfo
): AbstractTimerEditScreen(breakTimerInfo) {

    @Composable
    override fun ExtraFields() {
        // If the user presses the OK button on the timepicker, the time in the button should change
        var studyTime: Int by remember { mutableStateOf(breakTimerInfo.studyTime) }
        var breakTime: Int by remember { mutableStateOf(breakTimerInfo.breakTime) }

        val breakHms: HoursMinutesSeconds = Time(breakTime).getAsHMS()
        val studyHms: HoursMinutesSeconds = Time(studyTime).getAsHMS()
        TimePickerButton(studyHms) { _, hour, minute ->
            studyTime = hour * 60 * 60 + minute * 60
        }
        TimePickerButton(breakHms) { _, hour, minute ->
            breakTime = hour * 60 * 60 + minute * 60
        }
    }

}