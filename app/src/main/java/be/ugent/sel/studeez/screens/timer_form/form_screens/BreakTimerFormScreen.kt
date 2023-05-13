package be.ugent.sel.studeez.screens.timer_form.form_screens

import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.LabeledErrorTextField
import be.ugent.sel.studeez.common.composable.TimePickerCard
import be.ugent.sel.studeez.data.local.models.timer_info.PomodoroTimerInfo
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import be.ugent.sel.studeez.R.string as AppText


class BreakTimerFormScreen(
    private val breakTimerInfo: PomodoroTimerInfo
): AbstractTimerFormScreen(breakTimerInfo) {



    @Composable
    override fun ExtraFields() {
        // If the user presses the OK button on the timepicker, the time in the button should change

        TimePickerCard(R.string.studyTime, breakTimerInfo.studyTime) { newTime ->
            breakTimerInfo.studyTime = newTime
        }
        TimePickerCard(R.string.breakTime, breakTimerInfo.breakTime) { newTime ->
            breakTimerInfo.breakTime = newTime
        }

        valids["repeats"] = remember {mutableStateOf(true)}
        firsts["repeats"] = remember { mutableStateOf(true) }

        LabeledErrorTextField(
            initialValue = breakTimerInfo.repeats.toString(),
            label = R.string.repeats,
            errorText = AppText.repeats_error,
            isValid = valids.getValue("repeats"),
            isFirst = firsts.getValue("repeats"),
            keyboardType = KeyboardType.Decimal,
            predicate = { isNumber(it) }
        ) { correctlyTypedInt ->
            breakTimerInfo.repeats = correctlyTypedInt.toInt()
        }

    }
}

fun isNumber(text: String): Boolean {
    return text.matches(Regex("[1-9]+\\d*"))
}

@Preview
@Composable
fun BreakEditScreenPreview() {
    val pomodoroTimerInfo = PomodoroTimerInfo(
        "Breaky the Breaktimer",
        "Breaky is a breakdancer",
        10 * 60,
        60,
        5
    )
    StudeezTheme {
        BreakTimerFormScreen(pomodoroTimerInfo).invoke(onSaveClick = {}, {})
    }
}