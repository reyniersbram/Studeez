package be.ugent.sel.studeez.screens.timer_edit.editScreens

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.common.composable.TimePickerButton
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.data.local.models.timer_functional.Time
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo

class CustomTimerEditScreen(private val customTimerInfo: CustomTimerInfo): AbstractTimerEditScreen(customTimerInfo) {

    @Composable
    override fun ExtraFields() {
        // If the user presses the OK button on the timepicker, the time in the button should change
        var studyTime by remember { mutableStateOf(customTimerInfo.studyTime) }

        val hms: HoursMinutesSeconds = Time(studyTime).getAsHMS()
        TimePickerButton(hms) { _, hour, minute ->
            studyTime = hour * 60 * 60 + minute * 60
        }
    }


}

@Preview
@Composable
fun TimePickerPreview() {
    val customTimerInfo = CustomTimerInfo("custom", "my description", 25)
    CustomTimerEditScreen(customTimerInfo).ExtraFields()
}