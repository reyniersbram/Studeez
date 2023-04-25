package be.ugent.sel.studeez.screens.home

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.drawer.DrawerViewModel
import be.ugent.sel.studeez.common.composable.drawer.getDrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarViewModel
import be.ugent.sel.studeez.common.composable.navbar.getNavigationBarActions
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.resources

@Composable
fun HomeRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: HomeViewModel,
    drawerViewModel: DrawerViewModel,
    navBarViewModel: NavigationBarViewModel,
) {
    HomeScreen(
        onStartSessionClick = { viewModel.onStartSessionClick(open) },
        drawerActions = getDrawerActions(drawerViewModel, open, openAndPopUp),
        navigationBarActions = getNavigationBarActions(navBarViewModel, open),
    )
}

@Composable
fun HomeScreen(
    onStartSessionClick: () -> Unit,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {

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
    HomeScreen(
        onStartSessionClick = {},
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({}, {}, {}, {})
    )
}
