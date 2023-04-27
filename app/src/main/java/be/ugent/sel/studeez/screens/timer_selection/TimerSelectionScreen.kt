package be.ugent.sel.studeez.screens.timer_selection

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.composable.TimerEntry
import be.ugent.sel.studeez.common.composable.drawer.DrawerViewModel
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarViewModel
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TimerSelectionActions(
    val getAllTimers: () -> Flow<List<TimerInfo>>,
    val startSession: (TimerInfo) -> Unit,
)

fun getTimerSelectionActions(
    viewModel: TimerSelectionViewModel,
    open: (String) -> Unit,
): TimerSelectionActions {
    return TimerSelectionActions(
        getAllTimers = viewModel::getAllTimers,
        startSession = { viewModel.startSession(open, it) },
    )
}

@Composable
fun TimerSelectionRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    popUp: () -> Unit,
    getCurrentScreen: () -> String?,
    viewModel: TimerSelectionViewModel,
    drawerViewModel: DrawerViewModel,
    navBarViewModel: NavigationBarViewModel,
) {
    TimerSelectionScreen(
        timerSelectionActions = getTimerSelectionActions(viewModel, open),
        popUp = popUp
    )
}

@Composable
fun TimerSelectionScreen(
    timerSelectionActions: TimerSelectionActions,
    popUp: () -> Unit
) {
    val timers = timerSelectionActions.getAllTimers().collectAsState(initial = emptyList())
    SecondaryScreenTemplate(
        title = resources().getString(R.string.timers),
        popUp = popUp
    ) {
        LazyColumn {
            // All timers
            items(timers.value) { timerInfo ->
                TimerEntry(
                    timerInfo = timerInfo,
                    leftButton = {
                        StealthButton(
                            text = R.string.start,
                            onClick = { timerSelectionActions.startSession(timerInfo) }
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun TimerSelectionPreview() {
    TimerSelectionScreen(
        timerSelectionActions = TimerSelectionActions({ flowOf() }, {}),
        popUp = {}
    )
}