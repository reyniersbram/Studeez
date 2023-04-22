package be.ugent.sel.studeez.screens.timer_selection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.drawer.DrawerActions
import be.ugent.sel.studeez.screens.drawer.DrawerViewModel
import be.ugent.sel.studeez.screens.timer_overview.TimerEntry

@Composable
fun TimerSelectionScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: TimerSelectionViewModel = hiltViewModel()
) {

    val timers = viewModel.getAllTimers().collectAsState(initial = emptyList())
    val drawerViewModel: DrawerViewModel = hiltViewModel()
    val drawerActions = DrawerActions(
        onHomeButtonClick = { drawerViewModel.onHomeButtonClick(open) },
        onTimersClick = { drawerViewModel.onTimersClick(open) },
        onSettingsClick = { drawerViewModel.onSettingsClick(open) },
        onLogoutClick = { drawerViewModel.onLogoutClick(openAndPopUp) },
        onAboutClick = { drawerViewModel.onAboutClick(open) },
    )
    PrimaryScreenTemplate(
        title = resources().getString(R.string.timers),
        open = open,
        openAndPopUp = openAndPopUp,
        drawerActions = drawerActions,
    ) {

        LazyColumn(verticalArrangement = Arrangement.spacedBy(7.dp)) {

            // All timers
            items(timers.value) {
                TimerEntry(
                    timerInfo = it,
                    canDisplay = true,
                    buttonName = R.string.start
                ) { timerInfo ->
                    viewModel.startSession(open, timerInfo)
                }
            }
        }

    }
}