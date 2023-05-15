package be.ugent.sel.studeez.screens.session.sessionScreens

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

class BreakSessionScreen(
    private val funPomoDoroTimer: FunctionalPomodoroTimer,
    private var mediaplayer: MediaPlayer?
): AbstractSessionScreen() {

    @Composable
    override fun MidSection() {
        Dots()
    }

    @Composable
    fun Dots() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(funPomoDoroTimer.repeats - funPomoDoroTimer.breaksRemaining) {
                Dot(color = Color.DarkGray)
            }
            if (!funPomoDoroTimer.isInBreak) Dot(Color.Green) else Dot(Color.DarkGray)
            repeat(funPomoDoroTimer.breaksRemaining - 1) {
                Dot(color = Color.Gray)
            }
        }
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
    override fun motivationString(): String {
        if (funPomoDoroTimer.isInBreak) {
            return resources().getString(AppText.state_take_a_break)
        }

        if (funPomoDoroTimer.hasEnded()) {
            return resources().getString(AppText.state_done)
        }

        return resources().getString(AppText.state_focus)
    }

    override fun callMediaPlayer() {
        if (funPomoDoroTimer.hasEnded()) {
            mediaplayer?.let { it: MediaPlayer ->
                it.setOnCompletionListener {
                    it.release()
                    mediaplayer = null
                }
                it.start()
            }
        } else if (funPomoDoroTimer.hasCurrentCountdownEnded()) {
            mediaplayer?.start()
        }
    }
}

@Preview
@Composable
fun MidsectionPreview() {
    val funPomoDoroTimer = FunctionalPomodoroTimer(15, 60, 5)
    val breakSessionScreen = BreakSessionScreen(funPomoDoroTimer, MediaPlayer())
    breakSessionScreen.MidSection()
}