package be.ugent.sel.studeez.screens.session

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SessionScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
) {
    SecondaryScreenTemplate(
        title = resources().getString(R.string.start_session),
        popUp = {},
        content = { Timer() }
    )
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
        Text(
            text = viewModel.getTimer().getViewString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            fontSize = 30.sp
        )
    }
}