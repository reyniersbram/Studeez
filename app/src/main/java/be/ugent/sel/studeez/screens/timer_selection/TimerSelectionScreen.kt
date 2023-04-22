package be.ugent.sel.studeez.screens.timer_selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.TimerEntry
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.drawer.DrawerActions
import be.ugent.sel.studeez.screens.drawer.getDrawerActions
import be.ugent.sel.studeez.screens.navbar.NavigationBarActions
import be.ugent.sel.studeez.screens.navbar.getNavigationBarActions
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
    viewModel: TimerSelectionViewModel,
) {
    TimerSelectionScreen(
        timerSelectionActions = getTimerSelectionActions(viewModel, open),
        drawerActions = getDrawerActions(hiltViewModel(), open, openAndPopUp),
        navigationBarActions = getNavigationBarActions(hiltViewModel(), open),
    )
}

@Composable
fun TimerSelectionScreen(
    timerSelectionActions: TimerSelectionActions,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {
    val timers = timerSelectionActions.getAllTimers().collectAsState(initial = emptyList())
    PrimaryScreenTemplate(
        title = resources().getString(R.string.timers),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
    ) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            // All timers
            items(timers.value) {
                TimerEntry(
                    timerInfo = it,
                    showButton = true,
                    buttonName = R.string.start,
                    onButtonClick = timerSelectionActions.startSession
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
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({}, {}, {}, {}),
    )
}