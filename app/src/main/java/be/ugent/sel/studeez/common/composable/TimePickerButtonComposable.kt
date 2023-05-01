package be.ugent.sel.studeez.common.composable

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import java.util.*

// TODO codeduplicatie met Tibo, later wegdoen
@Composable
fun TimePickerButton(
    hoursMinutesSeconds: HoursMinutesSeconds,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    onTimeSetListener: OnTimeSetListener
) {
    val context = LocalContext.current
    Button(
        onClick = { pickDuration(context, onTimeSetListener) },
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = colors,
        border = border
    ) {
        Text(text = hoursMinutesSeconds.toString())
    }
}

// TODO idem codedup Tibo
private fun pickDuration(context: Context, listener: OnTimeSetListener) {
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR]
    val mMinute = mCalendar[Calendar.MINUTE]
    val mTimePickerDialog = TimePickerDialog(context, listener, mHour, mMinute, true)
    mTimePickerDialog.show()
}