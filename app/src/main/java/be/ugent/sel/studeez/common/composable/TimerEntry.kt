package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.*
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
    rightButton: @Composable () -> Unit = {},
    leftButton: @Composable () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.weight(1f)
        ) {
            Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
                leftButton()
            }

            Column(
                Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 11.dp
                )
            ) {
                Text(
                    text = timerInfo.name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
                Text(
                    text = timerInfo.description, fontWeight = FontWeight.Light, fontSize = 14.sp
                )
            }
        }

        Box(modifier = Modifier.align(alignment = Alignment.CenterVertically)) {
            rightButton()
        }
    }
}

@Preview
@Composable
fun TimerEntryPreview() {
    val timerInfo = CustomTimerInfo(
        "my preview timer", "This is the description of the timer", 60
    )
    TimerEntry(timerInfo = timerInfo) {
        StealthButton(text = R.string.edit) {}
    }
}

@Preview
@Composable
fun TimerDefaultEntryPreview() {
    val timerInfo = CustomTimerInfo(
        "Default preview timer", "This is the description of the timer", 60
    )
    TimerEntry(timerInfo = timerInfo) {}
}