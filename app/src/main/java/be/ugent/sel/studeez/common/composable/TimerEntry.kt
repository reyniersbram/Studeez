package be.ugent.sel.studeez.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo

@Composable
fun TimerEntry(
    timerInfo: TimerInfo,
    showButton: Boolean,
    @StringRes buttonName: Int = -1,
    onButtonClick: (TimerInfo) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = timerInfo.name, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )
            Text(
                text = timerInfo.description, fontWeight = FontWeight.Light, fontSize = 15.sp
            )
        }
        if (showButton) {
            StealthButton(buttonName) {
                onButtonClick(timerInfo)
            }
        }

    }
}

@Preview
@Composable
fun TimerEntryPreview() {
    val timerInfo = CustomTimerInfo(
        "my preview timer", "This is the description of the timer", 60
    )
    TimerEntry(timerInfo = timerInfo, true, buttonName = R.string.edit) { }
}

@Preview
@Composable
fun TimerDefaultEntryPreview() {
    val timerInfo = CustomTimerInfo(
        "Default preview timer", "This is the description of the timer", 60
    )
    TimerEntry(timerInfo = timerInfo, false, buttonName = R.string.edit) { }
}