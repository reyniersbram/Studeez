package be.ugent.sel.studeez.common.composable

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import java.util.*

@Composable
fun TimePickerCard(
    @StringRes text: Int,
    initialSeconds: Int,
    onTimeChosen: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fieldModifier(),
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fieldModifier(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = text),
                fontWeight = FontWeight.Medium
            )

            TimePickerButton(
                initialSeconds = initialSeconds,
                onTimeChosen = onTimeChosen
            )
        }
    }
}

@Composable
fun TimePickerButton(
    initialSeconds: Int,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    onTimeChosen: (Int) -> Unit
) {
    val context = LocalContext.current
    val timeState: MutableState<Int> = remember {
        mutableStateOf(initialSeconds)
    }

    Button(
        onClick = { pickDuration(context, onTimeChosen, timeState) },
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = colors,
        border = border
    ) {
        Text(text = HoursMinutesSeconds(timeState.value).toString())
    }
}

private fun pickDuration(context: Context, nextStep: (Int) -> Unit, timeState: MutableState<Int>) {
    val listener = OnTimeSetListener { _, hour, minute ->
        timeState.value = hour * 60 * 60 + minute * 60
        nextStep(timeState.value)
    }
    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR]
    val mMinute = mCalendar[Calendar.MINUTE]
    val mTimePickerDialog = TimePickerDialog(context, listener, mHour, mMinute, true)
    mTimePickerDialog.show()
}

@Preview
@Composable
fun TimePickerButtonPreview() {
    StudeezTheme {
        TimePickerButton(initialSeconds = 5 * 60 + 12, onTimeChosen = {})
    }
}

@Preview
@Composable
fun TimePickerCardPreview() {
    StudeezTheme {
        TimePickerCard(text = R.string.studyTime, initialSeconds = 5 * 60 + 12, onTimeChosen = {})
    }
}