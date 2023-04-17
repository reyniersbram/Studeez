package be.ugent.sel.studeez.screens.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.timers.TimerSelectionViewModel
import kotlinx.coroutines.delay

@Composable
fun SessionScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
) {
    PrimaryScreenTemplate(
        title = resources().getString(R.string.start_session),
        open = open,
        openAndPopUp = openAndPopUp
    ) {
        Timer()
    }
}

@Composable
fun Timer(viewModel: TimerSelectionViewModel = hiltViewModel()) {
    var tikker by remember { mutableStateOf(false) }
    LaunchedEffect(tikker) {
        delay(1000)
        viewModel.sessionTimer!!.tick()
        tikker = !tikker
    }

    val hms = viewModel.sessionTimer!!.getHoursMinutesSeconds()
    Column {
        Text(
            text = "${hms.hours} : ${hms.minutes} : ${hms.seconds}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 80.sp
        )
        Text(
            text = viewModel.sessionTimer!!.getViewString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            fontSize = 30.sp
        )
    }
}