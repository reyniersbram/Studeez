package be.ugent.sel.studeez.screens.timer_overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.composable.TimerEntry
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.drawer.DrawerViewModel
import be.ugent.sel.studeez.common.composable.drawer.getDrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarViewModel
import be.ugent.sel.studeez.common.composable.navbar.getNavigationBarActions
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TimerOverviewActions(
    val getUserTimers: () -> Flow<List<TimerInfo>>,
    val getDefaultTimers: () -> List<TimerInfo>,
    val onEditClick: (TimerInfo) -> Unit,
)

fun getTimerOverviewActions(
    viewModel: TimerOverviewViewModel,
): TimerOverviewActions {
    return TimerOverviewActions(
        getUserTimers = viewModel::getUserTimers,
        getDefaultTimers = viewModel::getDefaultTimers,
        onEditClick = { viewModel.update(it) },
    )
}

@Composable
fun TimerOverviewRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: TimerOverviewViewModel,
    drawerViewModel: DrawerViewModel,
    navBarViewModel: NavigationBarViewModel,
) {
    TimerOverviewScreen(
        timerOverviewActions = getTimerOverviewActions(viewModel),
        drawerActions = getDrawerActions(drawerViewModel, open, openAndPopUp),
        navigationBarActions = getNavigationBarActions(navBarViewModel, open),
    )
}

@Composable
fun TimerOverviewScreen(
    timerOverviewActions: TimerOverviewActions,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {

    val timers = timerOverviewActions.getUserTimers().collectAsState(initial = emptyList())

    // TODO moet geen primary screen zijn: geen navbar nodig
    PrimaryScreenTemplate(
        title = resources().getString(R.string.timers),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
    ) {
        Column {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                // Default Timers, cannot be edited
                items(timerOverviewActions.getDefaultTimers()) {
                    TimerEntry(timerInfo = it) {}
                }
                // User timers, can be edited
                items(timers.value) { timerInfo ->
                    TimerEntry(
                        timerInfo = timerInfo,
                    ) {
                        StealthButton(
                            text = R.string.edit,
                            onClick = { timerOverviewActions.onEditClick(timerInfo) }
                        )
                    }

                }
            }
            BasicButton(R.string.add_timer, Modifier.basicButton()) {
                // TODO
            }
        }
    }
}

@Preview
@Composable
fun TimerOverviewPreview() {
    val customTimer = CustomTimerInfo(
        "my preview timer", "This is the description of the timer", 60
    )
    TimerOverviewScreen(
        timerOverviewActions = TimerOverviewActions(
            { flowOf() },
            { listOf(customTimer, customTimer) },
            {}),
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({}, {}, {}, {})
    )
}
