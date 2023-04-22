package be.ugent.sel.studeez.screens.home

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.drawer.DrawerActions
import be.ugent.sel.studeez.screens.drawer.DrawerViewModel
import be.ugent.sel.studeez.screens.navbar.NavigationBarActions
import be.ugent.sel.studeez.screens.navbar.NavigationBarViewModel

@Composable
fun HomeRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: HomeViewModel,
) {
    HomeScreen(
        open = open,
        openAndPopUp = openAndPopUp,
        onStartSessionClick = { viewModel.onStartSessionClick(open) }
    )
}

@Composable
fun HomeScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    onStartSessionClick: () -> Unit,
) {
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
        title = resources().getString(R.string.home),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        action = { FriendsAction() }
    ) {
        BasicButton(R.string.start_session, Modifier.basicButton()) {
            onStartSessionClick()
        }
    }
}

@Composable
fun FriendsAction() {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = resources().getString(R.string.friends)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(open = {}, openAndPopUp = { _, _ -> run {} }, onStartSessionClick = {})
}
