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
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.resources

@Composable
fun HomeRoute(
    open: (String) -> Unit,
    viewModel: HomeViewModel,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {
    HomeScreen(
        onStartSessionClick = { viewModel.onStartSessionClick(open) },
        drawerActions = drawerActions,
        open = open,
        navigationBarActions = navigationBarActions,
    )
}

@Composable
fun HomeScreen(
    onStartSessionClick: () -> Unit,
    open: (String) -> Unit,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions
) {
    PrimaryScreenTemplate(
        title = resources().getString(R.string.home),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        // TODO barAction = { FriendsAction() }
    ) {
        Feed(open)
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
        navigationBarActions = NavigationBarActions({ false }, {}, {}, {}, {}, {}, {}, {}),
        open = {}
    )
}
