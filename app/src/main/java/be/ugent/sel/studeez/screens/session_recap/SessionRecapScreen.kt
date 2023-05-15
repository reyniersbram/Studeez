package be.ugent.sel.studeez.screens.session_recap

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.ImageBackgroundButton
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.data.local.models.timer_functional.Time

data class SessionRecapActions(
    val getSessionReport: () -> SessionReport,
    val saveSession: () -> Unit,
    val discardSession: () -> Unit
)

fun getSessionRecapActions(
    viewModel: SessionRecapViewModel,
    clearAndNavigate: (String) -> Unit,
): SessionRecapActions {
    return SessionRecapActions(
        viewModel::getSessionReport,
        { viewModel.saveSession(clearAndNavigate) },
        { viewModel.discardSession(clearAndNavigate) }
    )
}

@Composable
fun SessionRecapRoute(
    clearAndNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SessionRecapViewModel,
) {
    SessionRecapScreen(
        modifier = modifier,
        getSessionRecapActions(viewModel, clearAndNavigate)
    )
}

@Composable
fun SessionRecapScreen(modifier: Modifier, sessionRecapActions: SessionRecapActions) {
    val sessionReport: SessionReport = sessionRecapActions.getSessionReport()
    val studyTime: Int = sessionReport.studyTime
    val hms: HoursMinutesSeconds = Time(studyTime).getAsHMS()
    val (background1, setBackground1) = remember { mutableStateOf(Color.Transparent) }
    val (background2, setBackground2) = remember { mutableStateOf(Color.Transparent) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.congrats, hms),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
            fontSize = 30.sp,

            )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.how_did_it_go),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                fontSize = 30.sp
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                ImageBackgroundButton(
                    paint = painterResource(id = R.drawable.mood_1),
                    str = stringResource(id = R.string.good),
                    background2 = background2,
                    setBackground1 = setBackground2,
                    setBackground2 = setBackground1
                )

                ImageBackgroundButton(
                    paint = painterResource(id = R.drawable.mood_2),
                    str = stringResource(id = R.string.bad),
                    background2 = background1,
                    setBackground1 = setBackground1,
                    setBackground2 = setBackground2
                )
            }
        }

        Column {
            BasicButton(
                R.string.save, Modifier.basicButton()
            ) {
                sessionRecapActions.saveSession()
            }
            BasicButton(
                R.string.discard, Modifier.basicButton(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                sessionRecapActions.discardSession()
            }
        }
    }
}

@Preview
@Composable
fun SessionRecapScreenPreview() {
    SessionRecapScreen(
        modifier = Modifier,
        sessionRecapActions = SessionRecapActions(
            { SessionReport(
                studyTime = 100,
            ) },
            {},
            {},
        )
    )
}
