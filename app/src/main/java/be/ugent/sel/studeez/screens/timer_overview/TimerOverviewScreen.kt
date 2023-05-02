package be.ugent.sel.studeez.screens.timer_overview

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.DrawerScreenTemplate
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.composable.TimerEntry
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TimerOverviewActions(
    val getUserTimers: () -> Flow<List<TimerInfo>>,
    val getDefaultTimers: () -> List<TimerInfo>,
    val onEditClick: (TimerInfo) -> Unit,
    val open: (String) -> Unit,
)

fun getTimerOverviewActions(
    viewModel: TimerOverviewViewModel,
    open: (String) -> Unit,
): TimerOverviewActions {
    return TimerOverviewActions(
        getUserTimers = viewModel::getUserTimers,
        getDefaultTimers = viewModel::getDefaultTimers,
        onEditClick = { viewModel.update(it) },
        open = open
    )
}

@Composable
fun TimerOverviewRoute(
    open: (String) -> Unit,
    viewModel: TimerOverviewViewModel,
    drawerActions: DrawerActions,
) {
    TimerOverviewScreen(
        timerOverviewActions = getTimerOverviewActions(viewModel, open),
        drawerActions = drawerActions,
    )
}

@Composable
fun TimerOverviewScreen(
    timerOverviewActions: TimerOverviewActions,
    drawerActions: DrawerActions
) {

    val timers = timerOverviewActions.getUserTimers().collectAsState(initial = emptyList())

    DrawerScreenTemplate(
        title = resources().getString(R.string.timers),
        drawerActions = drawerActions
    ) {
        LazyColumn {
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
            item {
                BasicButton(R.string.add_timer, Modifier.basicButton()) {
                    timerOverviewActions.open(StudeezDestinations.ADD_TIMER_SCREEN)
                }
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
            {},
            {}
        ),
        drawerActions = DrawerActions({}, {}, {}, {}, {})
    )
}
