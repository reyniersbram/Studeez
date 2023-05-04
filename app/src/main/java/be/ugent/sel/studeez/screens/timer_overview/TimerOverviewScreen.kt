package be.ugent.sel.studeez.screens.timer_overview

import androidx.compose.foundation.layout.Column
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
    val onAddClick: () -> Unit
)

fun getTimerOverviewActions(
    viewModel: TimerOverviewViewModel,
    open: (String) -> Unit,
): TimerOverviewActions {
    return TimerOverviewActions(
        getUserTimers = viewModel::getUserTimers,
        getDefaultTimers = viewModel::getDefaultTimers,
        onEditClick = { viewModel.update(it, open) },
        onAddClick = { viewModel.onAddClick(open) }
    )
}

@Composable
fun TimerOverviewRoute(
    viewModel: TimerOverviewViewModel,
    drawerActions: DrawerActions,
    open: (String) -> Unit
) {
    TimerOverviewScreen(
        timerOverviewActions = getTimerOverviewActions(viewModel, open),
        drawerActions = drawerActions,
    )
}

@Composable
fun TimerOverviewScreen(
    timerOverviewActions: TimerOverviewActions,
    drawerActions: DrawerActions,
) {

    val timers = timerOverviewActions.getUserTimers().collectAsState(initial = emptyList())

    DrawerScreenTemplate(
        title = resources().getString(R.string.timers),
        drawerActions = drawerActions
    ) {
        Column { // TODO knop beneden
            LazyColumn {
                // Custom timer, select new duration each time
                item {
                    TimerEntry(timerInfo = CustomTimerInfo(
                        name = resources().getString(R.string.custom_name),
                        description = resources().getString(R.string.custom_name),
                        studyTime = 0
                    ))
                }
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

                // TODO uit lazy column
                item {
                    BasicButton(R.string.add_timer, Modifier.basicButton()) {
                        timerOverviewActions.onAddClick()
                    }
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
