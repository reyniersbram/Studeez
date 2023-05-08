package be.ugent.sel.studeez.screens.home

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.feed.*
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeRoute(
    open: (String) -> Unit,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    feedViewModel: FeedViewModel,
) {
    val feedUiState by feedViewModel.uiState.collectAsState()
    HomeScreen(
        drawerActions = drawerActions,
        open = open,
        navigationBarActions = navigationBarActions,
        feedActions = getFeedActions(feedViewModel, open),
        feedUiState = feedUiState,
    )
}

@Composable
fun HomeScreen(
    open: (String) -> Unit,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    feedActions: FeedActions,
    feedUiState: FeedUiState,
) {
    PrimaryScreenTemplate(
        title = resources().getString(R.string.home),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        // TODO barAction = { FriendsAction() }
    ) {
        Feed(feedActions, feedUiState)
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
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({ false }, {}, {}, {}, {}, {}, {}, {}),
        open = {},
        feedActions = FeedActions({ flowOf() }, { _, _ -> run {} }),
        feedUiState = FeedUiState.Loading,
    )
}
