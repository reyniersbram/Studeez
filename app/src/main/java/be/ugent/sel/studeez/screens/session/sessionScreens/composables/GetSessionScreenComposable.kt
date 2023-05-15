package be.ugent.sel.studeez.screens.session.sessionScreens.composables

import android.media.MediaPlayer
import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimerVisitor
import be.ugent.sel.studeez.screens.session.SessionActions
import be.ugent.sel.studeez.screens.session.sessionScreens.AbstractSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.BreakSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.CustomSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.EndlessSessionScreen

class GetSessionScreenComposable(
    private val mediaplayer: MediaPlayer?,
    private val open: (String) -> Unit,
    private val sessionActions: SessionActions
    ) :
    FunctionalTimerVisitor<@Composable () -> Unit> {

    override fun visitFunctionalCustomTimer(functionalCustomTimer: FunctionalCustomTimer): @Composable () -> Unit {
        return { CustomTimerSessionScreenComposable(
                open = open,
                sessionActions = sessionActions,
                customTimer = functionalCustomTimer
            )
        }
    }

    override fun visitFunctionalEndlessTimer(functionalEndlessTimer: FunctionalEndlessTimer): @Composable () -> Unit {
        return {
            EndlessTimerSessionScreenComposable(
                open = open,
                sessionActions = sessionActions,
            )
        }
    }

    override fun visitFunctionalBreakTimer(functionalPomodoroTimer: FunctionalPomodoroTimer): @Composable () -> Unit {
        return {
            BreakSessionScreenComposable(
                open = open,
                sessionActions = sessionActions,
                pomodoroTimer = functionalPomodoroTimer
            )
        }
    }
}