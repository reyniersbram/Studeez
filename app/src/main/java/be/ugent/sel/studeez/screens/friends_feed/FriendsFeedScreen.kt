package be.ugent.sel.studeez.screens.friends_feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.TimerEntry
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun FriendsFeedRoute(
    viewModel: FriendsFeedViewModel,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions
) {
    FriendsFeedScreen(
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        viewModel = viewModel
    )
}

@Composable
fun FriendsFeedScreen(
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    viewModel: FriendsFeedViewModel
) {
    PrimaryScreenTemplate(
        title = resources().getString(AppText.upcoming_sessions),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions
    ) {

        val friendsSessions = viewModel.getFriendsSessions().collectAsState(initial = emptyList())
        LazyColumn() {
            // Default Timers, cannot be edited
            items(friendsSessions.value) {
                FriendsFeedEntry(name = it.first, sessions = it.second)
                Divider()
            }
        }

    }
}

@Composable
fun FriendsFeedEntry(name: String, sessions: List<Task>) {
    Column() {
        Text(text = "$name Werkte ")
        sessions.forEach {
            Text(text = "${HoursMinutesSeconds(it.time)} aan ${it.name}")
        }
    }
}