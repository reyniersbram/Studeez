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
import be.ugent.sel.studeez.common.composable.feed.Feed
import be.ugent.sel.studeez.common.composable.feed.FeedUiState
import be.ugent.sel.studeez.common.composable.feed.FeedViewModel
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.resources

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
        feedUiState = feedUiState,
        continueTask = { subjectId, taskId -> feedViewModel.continueTask(open, subjectId, taskId) },
        onEmptyFeedHelp = { feedViewModel.onEmptyFeedHelp(open) }
    )
}

@Composable
fun HomeScreen(
    open: (String) -> Unit,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    feedUiState: FeedUiState,
    continueTask: (String, String) -> Unit,
    onEmptyFeedHelp: () -> Unit,
) {
    PrimaryScreenTemplate(
        title = resources().getString(R.string.home),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        // TODO barAction = { FriendsAction() }
    ) {
        Feed(feedUiState, continueTask, onEmptyFeedHelp)
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
        feedUiState = FeedUiState.Succes(
            mapOf(
                "08 May 2023" to listOf(
                    FeedEntry(
                        argb_color = 0xFFABD200,
                        subJectName = "Test Subject",
                        taskName = "Test Task",
                        totalStudyTime = 600,
                    ),
                    FeedEntry(
                        argb_color = 0xFFFFD200,
                        subJectName = "Test Subject",
                        taskName = "Test Task",
                        totalStudyTime = 20,
                    ),
                ),
                "09 May 2023" to listOf(
                    FeedEntry(
                        argb_color = 0xFFFD1200,
                        subJectName = "Test Subject",
                        taskName = "Test Task",
                    ),
                    FeedEntry(
                        argb_color = 0xFFFF5C89,
                        subJectName = "Test Subject",
                        taskName = "Test Task",
                    ),
                )
            )
        ),
        continueTask = { _, _ -> run {} },
        onEmptyFeedHelp = {}
    )
}
