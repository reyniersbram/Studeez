package be.ugent.sel.studeez.screens.session.sessionScreens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.session.SessionActions
import be.ugent.sel.studeez.screens.session.sessionScreens.SoundPlayer

@Composable
fun BreakSessionScreenComposable(
    open: (String) -> Unit,
    sessionActions: SessionActions,
    pomodoroTimer: FunctionalPomodoroTimer,
    soundPlayer: SoundPlayer,
) {
    SessionScreen(
        open = open,
        sessionActions = sessionActions,
        midSection = { Dots(pomodoroTimer = pomodoroTimer) },
        callMediaPlayer = { soundPlayer.playOn(pomodoroTimer.hasCurrentCountdownEnded()) },
        motivationString = { motivationString (pomodoroTimer = pomodoroTimer) }
    )
}

@Composable
private fun Dots(pomodoroTimer: FunctionalPomodoroTimer): Int {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pomodoroTimer.repeats - pomodoroTimer.breaksRemaining) {
            Dot(color = Color.DarkGray)
        }
        if (!pomodoroTimer.isInBreak) Dot(Color.Green) else Dot(Color.DarkGray)
        repeat(pomodoroTimer.breaksRemaining - 1) {
            Dot(color = Color.Gray)
        }
    }
    return pomodoroTimer.breaksRemaining
}

@Composable
private fun Dot(color: Color) {
    Box(modifier = Modifier
        .padding(5.dp)
        .size(10.dp)
        .clip(CircleShape)
        .background(color))
}


@Composable
private fun motivationString(pomodoroTimer: FunctionalPomodoroTimer): String {
    if (pomodoroTimer.isInBreak) {
        return resources().getString(R.string.state_take_a_break)
    }

    if (pomodoroTimer.hasEnded()) {
        return resources().getString(R.string.state_done)
    }

    return resources().getString(R.string.state_focus)
}