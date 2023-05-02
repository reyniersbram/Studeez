package be.ugent.sel.studeez.common.composable.navbar

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun BasicTimePicker(
    onHoursChange: (Int) -> Unit,
    onMinutesChange: (Int) -> Unit,
    Hours: Int,
    Minutes: Int,
): TimePickerDialog {
    return TimePickerDialog(
        LocalContext.current,
        { _, mHour: Int, mMinute: Int ->
            onHoursChange(mHour)
            onMinutesChange(mMinute)
        },
        Hours,
        Minutes,
        true
    )
}
