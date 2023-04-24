package be.ugent.sel.studeez.screens.session

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.navigation.StudeezDestinations
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer.StudyState
import be.ugent.sel.studeez.data.local.models.timer_functional.Time
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.time.Duration.Companion.seconds

@Composable
fun SessionScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SessionViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val mediaplayer = MediaPlayer.create(context, uri)
    mediaplayer.isLooping = false

    InvisibleSessionManager.setNewViewModel(viewModel = viewModel)

    Column(
       modifier = Modifier.padding(10.dp)
   ) {
        Timer(viewModel, mediaplayer)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        ) {
            TextButton(
                onClick = {
                    mediaplayer.stop()
                    mediaplayer.release()
                    open(StudeezDestinations.HOME_SCREEN)
                    InvisibleSessionManager.isSession = false
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

private operator fun Time.minus(time: Time): Time {
    return Time(this.time - time.time)

}

@Composable
private fun Timer(viewModel: SessionViewModel = hiltViewModel(), mediaplayer: MediaPlayer) {
    var tikker by remember { mutableStateOf(false) }
    LaunchedEffect(tikker) {
        delay(1.seconds)
        viewModel.getTimer().tick()
        tikker = !tikker
    }

    if (viewModel.getTimer().hasCurrentCountdownEnded() && !viewModel.getTimer().hasEnded()) {
        mediaplayer.start()
    }

    val hms = viewModel.getTimer().getHoursMinutesSeconds()
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
        val stateString: String = when (viewModel.getTimer().view) {
            StudyState.DONE -> resources().getString(R.string.state_done)
            StudyState.FOCUS -> resources().getString(R.string.state_focus)
            StudyState.BREAK -> resources().getString(R.string.state_take_a_break)
            StudyState.FOCUS_REMAINING ->
                (viewModel.getTimer() as FunctionalPomodoroTimer?)?.breaksRemaining?.let {
                    resources().getQuantityString(R.plurals.state_focus_remaining, it, it)
            }.toString()
        }

        Text(
            text = stateString,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            fontSize = 30.sp
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
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
                    text = viewModel.getTask(),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 20.dp)
                )
            }
        }
    }
}

object InvisibleSessionManager {
    lateinit var viewModel: SessionViewModel
    var isSession: Boolean = false

    fun setNewViewModel(viewModel: SessionViewModel) {
        isSession = true
        this.viewModel = viewModel
    }

    suspend fun updateTimer() {
        if (isSession) {
            while (true) {
                delay(1.seconds)
                viewModel.getTimer().tick()
            }
        }
    }


}