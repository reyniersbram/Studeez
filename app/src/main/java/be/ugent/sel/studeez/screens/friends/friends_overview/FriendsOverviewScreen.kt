package be.ugent.sel.studeez.screens.friends.friends_overview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.ProfilePicture
import be.ugent.sel.studeez.common.composable.SearchField
import be.ugent.sel.studeez.common.composable.drawer.DrawerEntry
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.Friendship
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import be.ugent.sel.studeez.R.string as AppText

data class FriendsOverviewActions(
    val getFriendsFlow: () -> Flow<List<Pair<User, Friendship>>>,
    val searchFriends: () -> Unit,
    val onQueryStringChange: (String) -> Unit,
    val onSubmit: () -> Unit,
    val viewProfile: (String) -> Unit,
    val removeFriend: (Friendship) -> Unit
)

fun getFriendsOverviewActions(
    viewModel: FriendsOverviewViewModel,
    open: (String) -> Unit
): FriendsOverviewActions {
    return FriendsOverviewActions(
        getFriendsFlow = viewModel::getAllFriends,
        searchFriends = { viewModel.searchFriends(open) },
        onQueryStringChange = viewModel::onQueryStringChange,
        onSubmit = { viewModel.onSubmit(open) },
        viewProfile = { userId ->
            viewModel.viewProfile(userId, open)
        },
        removeFriend = viewModel::removeFriend
    )
}

@Composable
fun FriendsOveriewRoute(
    open: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: FriendsOverviewViewModel
) {
    val uiState by viewModel.uiState
    FriendsOverviewScreen(
        popUp = popUp,
        uiState = uiState,
        friendsOverviewActions = getFriendsOverviewActions(
            viewModel = viewModel,
            open = open
        )
    )
}

@Composable
fun FriendsOverviewScreen(
    popUp: () -> Unit,
    uiState: FriendsOverviewUiState,
    friendsOverviewActions: FriendsOverviewActions
) {
    val friends = friendsOverviewActions.getFriendsFlow().collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // TODO Link to each other
                    SearchField(
                        value = uiState.queryString,
                        onValueChange = friendsOverviewActions.onQueryStringChange,
                        onSubmit = friendsOverviewActions.onSubmit,
                        label = AppText.search_friends
                    )
                },
                navigationIcon = {
                    IconButton(onClick = popUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = resources().getString(R.string.go_back)
                        )
                    }
                }
                // TODO Add inbox action
            )
        }
    ) { paddingValues ->
        LazyColumn (
            modifier = Modifier.padding(paddingValues)
        ) {
            if (friends.value.isEmpty()) {
                // Show a quick button to search friends when the user does not have any friends yet.
                item {
                    BasicButton(
                        text = AppText.no_friends,
                        modifier = Modifier.basicButton()
                    ) {
                        friendsOverviewActions.searchFriends()
                    }
                }
            }

            items(friends.value) { friend ->
                FriendsEntry(
                    user = friend.first,
                    friendship = friend.second,
                    viewProfile = { userId -> friendsOverviewActions.viewProfile(userId) },
                    removeFriend = friendsOverviewActions.removeFriend
                )
            }
        }
    }
}

@Preview
@Composable
fun FriendsOverviewPreview() {
    StudeezTheme {
        FriendsOverviewScreen(
            popUp = {},
            uiState = FriendsOverviewUiState(""),
            friendsOverviewActions = FriendsOverviewActions(
                getFriendsFlow = { emptyFlow() },
                searchFriends = {},
                onQueryStringChange = {},
                onSubmit = {},
                viewProfile = {},
                removeFriend = {}
            )
        )
    }
}

@Composable
fun FriendsEntry(
    user: User,
    friendship: Friendship,
    viewProfile: (String) -> Unit,
    removeFriend: (Friendship) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 7.dp),
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
        ) {
            ProfilePicture()
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column (
                modifier = Modifier
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = user.username,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${resources().getString(AppText.app_name)} ${resources().getString(AppText.friend)}",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                FriendsOverviewDropDown(
                    friendship = friendship,
                    viewProfile = viewProfile,
                    removeFriend = removeFriend
                )
            }
        }
    }
}

@Preview
@Composable
fun FriendsEntryPreview() {
    StudeezTheme {
        FriendsEntry(
            user = User(
                id = "",
                username = "Tibo De Peuter",
                biography = "short bio"
            ),
            friendship = Friendship(
                id = "",
                friendId = "someId",
                friendsSince = Timestamp.now(),
                accepted = true
            ),
            viewProfile = {},
            removeFriend = {}
        )
    }
}

@Composable
fun FriendsOverviewDropDown(
    friendship: Friendship,
    viewProfile: (String) -> Unit,
    removeFriend: (Friendship) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_horizontal),
            contentDescription = resources().getString(AppText.view_more),
            modifier = Modifier.fillMaxSize()
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DrawerEntry(
            icon = Icons.Default.Person,
            text = stringResource(id = AppText.show_profile)
        ) {
            viewProfile(friendship.friendId)
        }
        DrawerEntry(
            icon = Icons.Default.Delete,
            text = stringResource(id = AppText.remove_friend)
        ) {
            removeFriend(friendship)
            expanded = false
        }
    }
}

@Preview
@Composable
fun FriendsOverviewDropDownPreview() {
    StudeezTheme {
        FriendsOverviewDropDown(
            friendship = Friendship(
                id = "",
                friendId = "someId",
                friendsSince = Timestamp.now(),
                accepted = true
            ),
            viewProfile = {},
            removeFriend = { }
        )
    }
}