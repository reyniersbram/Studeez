package be.ugent.sel.studeez.screens.timer_form.form_screens

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.common.composable.TimePickerCard
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import be.ugent.sel.studeez.R.string as AppText

class CustomTimerFormScreen(
    private val customTimerInfo: CustomTimerInfo
) : AbstractTimerFormScreen(customTimerInfo) {

    @Composable
    override fun ExtraFields() {
        TimePickerCard(
            text = AppText.studyTime,
            initialSeconds = customTimerInfo.studyTime
        ) { newTime ->
            customTimerInfo.studyTime = newTime
        }
    }


}

@Preview
@Composable
fun CustomEditScreenPreview() {
    val customTimerInfo = CustomTimerInfo("custom", "my description", 25)
    StudeezTheme {
        CustomTimerFormScreen(customTimerInfo).invoke(onSaveClick = {})
    }
}