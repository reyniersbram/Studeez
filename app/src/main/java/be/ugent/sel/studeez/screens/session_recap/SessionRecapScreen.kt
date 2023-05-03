package be.ugent.sel.studeez.screens.session_recap

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
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
    openAndPopUp: (String, String) -> Unit,
): SessionRecapActions {
    return SessionRecapActions(
        viewModel::getSessionReport,
        {viewModel.saveSession(openAndPopUp)},
        {viewModel.discardSession(openAndPopUp)}
    )
}

@Composable
fun SessionRecapRoute(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SessionRecapViewModel,
) {
    SessionRecapScreen(
        modifier = modifier,
        getSessionRecapActions(viewModel, openAndPopUp)
    )
}

@Composable
fun SessionRecapScreen(modifier: Modifier, sessionRecapActions: SessionRecapActions) {
    val sessionReport: SessionReport = sessionRecapActions.getSessionReport()
    val studyTime: Int = sessionReport.studyTime
    val hms: HoursMinutesSeconds = Time(studyTime).getAsHMS()
    Column(
        modifier = modifier
    ) {
        Text(text = "You studied: $hms")

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
