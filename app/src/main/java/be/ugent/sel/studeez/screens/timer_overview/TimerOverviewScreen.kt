package be.ugent.sel.studeez.screens.timer_overview

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.drawer.DrawerActions
import be.ugent.sel.studeez.screens.drawer.DrawerViewModel
import be.ugent.sel.studeez.screens.navbar.NavigationBarActions
import be.ugent.sel.studeez.screens.navbar.NavigationBarViewModel

@Composable
fun TimerOverviewScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: TimerOverviewViewModel = hiltViewModel()
) {

    val timers = viewModel.getUserTimers().collectAsState(initial = emptyList())
    val drawerViewModel: DrawerViewModel = hiltViewModel()
    val drawerActions = DrawerActions(
        onHomeButtonClick = { drawerViewModel.onHomeButtonClick(open) },
        onTimersClick = { drawerViewModel.onTimersClick(open) },
        onSettingsClick = { drawerViewModel.onSettingsClick(open) },
        onLogoutClick = { drawerViewModel.onLogoutClick(openAndPopUp) },
        onAboutClick = { drawerViewModel.onAboutClick(open) },
    )
    val navigationBarViewModel: NavigationBarViewModel = hiltViewModel()
    val navigationBarActions = NavigationBarActions(
        onHomeClick = { navigationBarViewModel.onHomeClick(open) },
        onTasksClick = { navigationBarViewModel.onTasksClick(open) },
        onSessionsClick = { navigationBarViewModel.onSessionsClick(open) },
        onProfileClick = { navigationBarViewModel.onProfileClick(open) },
    )
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
                items(viewModel.getDefaultTimers()) {
                    TimerEntry(timerInfo = it, canDisplay = false)
                }

                // User timers, can be edited
                items(timers.value) {
                    TimerEntry(timerInfo = it, true, R.string.edit) { timerInfo ->
                        viewModel.update(timerInfo)
                    }
                }
            }
            BasicButton(R.string.add_timer, Modifier.basicButton()) {
                // TODO
            }
        }

    }
}

@Composable
fun TimerEntry(
    timerInfo: TimerInfo,
    canDisplay: Boolean,
    @StringRes buttonName: Int = -1,
    buttonFunction: (TimerInfo) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = timerInfo.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = timerInfo.description,
                fontWeight = FontWeight.Light,
                fontSize = 15.sp
            )
        }
        if (canDisplay) {
            StealthButton(buttonName) {
                buttonFunction(timerInfo)
            }
        }

    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun TimerEntryPreview() {
    val timerInfo = CustomTimerInfo(
        "my preview timer",
        "This is the description of the timer",
        60
    )
    Scaffold() {
        Column() {
            TimerEntry(timerInfo = timerInfo, true, buttonName = R.string.edit) { }
            TimerEntry(timerInfo = timerInfo, true, buttonName = R.string.edit) { }
            TimerEntry(timerInfo = timerInfo, true, buttonName = R.string.edit) { }
        }
    }
}