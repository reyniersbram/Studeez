package be.ugent.sel.studeez.screens.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer.StudyState
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SessionScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
) {
    Timer()
}

@Composable
fun Timer(viewModel: SessionViewModel = hiltViewModel()) {


    var ticker by remember { mutableStateOf(false) }
    LaunchedEffect(ticker) {
        delay(1.seconds)
        viewModel.getTimer().tick()
        ticker = !ticker
    }

    val hms = viewModel.getTimer().getHoursMinutesSeconds()
    Column {
        Text(
            text = "${hms.hours} : ${hms.minutes} : ${hms.seconds}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 80.sp
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
    }


}