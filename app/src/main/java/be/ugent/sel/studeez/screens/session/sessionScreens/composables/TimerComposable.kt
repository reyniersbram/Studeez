package be.ugent.sel.studeez.screens.session.sessionScreens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.screens.session.SessionActions
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun Timer(
    sessionActions: SessionActions,
    motivationString: @Composable () -> String,
    midSection: @Composable () -> Unit
) {
    var tikker by remember { mutableStateOf(false) }
    LaunchedEffect(tikker) {
        delay(1.seconds)
        sessionActions.getTimer().tick()
        // callMediaPlayer() TODO
        tikker = !tikker
    }

    val hms = sessionActions.getTimer().getHoursMinutesSeconds()
    Column {

        TimerClock(hms)
        MotivationText(text = motivationString())


        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Blue, RoundedCornerShape(32.dp))
            ) {
                TaskText(taskName = sessionActions.getTask())
            }
        }
    }
}

@Composable
fun TimerClock(hms: HoursMinutesSeconds) {
    Text(
        text = hms.toString(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
    )
}

@Composable
fun MotivationText(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Light,
        fontSize = 30.sp
    )
}

@Composable
fun TaskText(taskName: String) {
    Text(
        text = taskName,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 20.dp)
    )
}