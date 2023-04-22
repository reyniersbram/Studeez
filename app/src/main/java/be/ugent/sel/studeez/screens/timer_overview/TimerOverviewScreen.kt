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
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.TimerEntry
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.drawer.DrawerActions
import be.ugent.sel.studeez.screens.drawer.getDrawerActions
import be.ugent.sel.studeez.screens.navbar.NavigationBarActions
import be.ugent.sel.studeez.screens.navbar.getNavigationBarActions
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
) {
    TimerOverviewScreen(
        timerOverviewActions = getTimerOverviewActions(viewModel),
        drawerActions = getDrawerActions(hiltViewModel(), open, openAndPopUp),
        navigationBarActions = getNavigationBarActions(hiltViewModel(), open),
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
                    TimerEntry(timerInfo = it, showButton = false)
                }
                // User timers, can be edited
                items(timers.value) {
                    TimerEntry(
                        timerInfo = it,
                        true,
                        R.string.edit,
                        onButtonClick = timerOverviewActions.onEditClick
                    )

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
            { flowOf(listOf()) },
            { listOf(customTimer, customTimer) },
            {}),
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({}, {}, {}, {})
    )
}
