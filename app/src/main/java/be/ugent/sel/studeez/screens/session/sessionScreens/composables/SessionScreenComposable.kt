package be.ugent.sel.studeez.screens.session.sessionScreens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.screens.session.SessionActions

@Composable
fun SessionScreen(
    open: (String) -> Unit,
    sessionActions: SessionActions,
    midSection: @Composable () -> Unit = {},
    motivationString: @Composable () -> String,

) {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Timer(
            sessionActions = sessionActions,
            motivationString = motivationString,
            midSection = midSection
        )
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(50.dp)
        ) {
            EndSessionButton(sessionActions = sessionActions)
        }
    }
}

@Composable
fun EndSessionButton(sessionActions: SessionActions) {
    TextButton(
        onClick = {
            sessionActions.releaseMediaPlayer
            sessionActions.endSession()
        },
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .border(1.dp, Color.Red, RoundedCornerShape(32.dp))
            .background(Color.Transparent)
    ) {
        EndsessionText()
    }
}

@Composable
fun EndsessionText() {
    Text(
        text = "End session",
        color = Color.Red,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(1.dp)
    )
}