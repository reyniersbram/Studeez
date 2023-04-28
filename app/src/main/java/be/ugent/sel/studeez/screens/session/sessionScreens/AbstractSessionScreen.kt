package be.ugent.sel.studeez.screens.session.sessionScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.session.SessionActions
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

abstract class AbstractSessionScreen {

    @Composable
    operator fun invoke(
        open: (String) -> Unit,
        sessionActions: SessionActions,
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Timer(
                sessionActions = sessionActions,
            )
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp)
            ) {
                TextButton(
                    onClick = {
                        sessionActions.releaseMediaPlayer
                        open(StudeezDestinations.HOME_SCREEN)
                        // Vanaf hier ook naar report gaan als "end session" knop word ingedrukt
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .border(1.dp, Color.Red, RoundedCornerShape(32.dp))
                        .background(Color.Transparent)
                ) {
                    Text(
                        text = "End session",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(1.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun Timer(
        sessionActions: SessionActions,
    ) {
        var tikker by remember { mutableStateOf(false) }
        LaunchedEffect(tikker) {
            delay(1.seconds)
            sessionActions.getTimer().tick()
            tikker = !tikker
        }

        val hms = sessionActions.getTimer().getHoursMinutesSeconds()
        Column {
            Text(
                text = "${hms.hours} : ${hms.minutes} : ${hms.seconds}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
            )

            Text(
                text = motivationString(),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                fontSize = 30.sp
            )

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
                    Text(
                        text = sessionActions.getTask(),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 20.dp)
                    )
                }
            }
        }
    }

    @Composable
    abstract fun motivationString(): String

}

@Preview
@Composable
fun TimerPreview() {
    val sessionScreen = object : AbstractSessionScreen() {
        @Composable
        override fun motivationString(): String = "Test"

    }
    sessionScreen.Timer(sessionActions = SessionActions({ FunctionalEndlessTimer() }, { "Preview" }, {}, {}))
}