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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.domain.implementation.LogServiceImpl
import be.ugent.sel.studeez.navigation.StudeezDestinations
import kotlinx.coroutines.delay

var timerEnd = false

@Composable
fun SessionScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SessionViewModel = hiltViewModel()
) {
    Column(
       modifier = Modifier.padding(10.dp)
   ) {
        Timer(viewModel)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        ) {
            TextButton(
                onClick = { open(StudeezDestinations.HOME_SCREEN) },
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
private fun Timer(viewModel: SessionViewModel = hiltViewModel()) {
    var tikker by remember { mutableStateOf(false) }
    LaunchedEffect(tikker) {
        delay(1000)
        viewModel.getTimer().tick()
        tikker = !tikker
    }

    val context = LocalContext.current
    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val mediaplayer = MediaPlayer.create(context, uri)
    mediaplayer.setOnCompletionListener {
        mediaplayer.stop()
        if (timerEnd) {
            mediaplayer.release()
        }
    }

    if (viewModel.getTimer().hasCurrentCountdownEnded() && !viewModel.getTimer().hasEnded()) {
        mediaplayer.start()
    }

    if (!timerEnd && viewModel.getTimer().hasEnded()) {
        mediaplayer.start()
        timerEnd = true // Placeholder, vanaf hier moet het report opgestart worden en de sessie afgesloten
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
        Text(
            text = viewModel.getTimer().getViewString(),
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

@Preview
@Composable
fun Screen() {
    val viewModel = SessionViewModel(LogServiceImpl())
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Timer(viewModel)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        ) {
            TextButton(
                onClick = {},
                modifier = Modifier
                    .padding(vertical = 1.dp, horizontal = 20.dp)
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