package be.ugent.sel.studeez.screens.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
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
    val searchFriends: () -> Unit
)

fun getFriendsOverviewActions(
    viewModel: FriendsOverviewViewModel,
    open: (String) -> Unit
): FriendsOverviewActions {
    return FriendsOverviewActions(
        getFriendsFlow = viewModel::getAllFriends,
        searchFriends = { viewModel.searchFriends(open) }
    )
}

@Composable
fun FriendsOveriewRoute(
    open: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: FriendsOverviewViewModel
) {
    FriendsOverviewScreen(
        friendsOverviewActions = getFriendsOverviewActions(
            viewModel = viewModel,
            open = open
        ),
        popUp = popUp
    )
}

@Composable
fun FriendsOverviewScreen(
    friendsOverviewActions: FriendsOverviewActions,
    popUp: () -> Unit
) {
    val friends = friendsOverviewActions.getFriendsFlow().collectAsState(initial = emptyList())
    
    SecondaryScreenTemplate(
        title = "TODO there needs to be a search field here", // TODO
        popUp = popUp
    ) {
        LazyColumn {
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
                    friendship = friend.second
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
            friendsOverviewActions = FriendsOverviewActions(
                getFriendsFlow = { emptyFlow() },
                searchFriends = {}
            ),
            popUp = {}
        )
    }
}

@Composable
fun FriendsEntry(
    user: User,
    friendship: Friendship
) {
    // TODO Styling
    Row (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.15f)
                .background(MaterialTheme.colors.primary, CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_visibility_on),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.Center),
                tint = MaterialTheme.colors.onPrimary
            )
        }

        Box (
            modifier = Modifier
                .fillMaxWidth(0.65f)
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
        }

        Box(
            modifier = Modifier.fillMaxWidth(0.15f)
        ) {
            ThreeDots(friendship = friendship)
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
            )
        )
    }
}

@Composable
fun ThreeDots(
    friendship: Friendship
) {
    IconButton(
        onClick = { /* TODO Open dropdown */ }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_horizontal),
            contentDescription = resources().getString(AppText.view_more),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun ThreeDotsPreview() {
    StudeezTheme {
        ThreeDots(
            friendship = Friendship(
                id = "",
                friendId = "someId",
                friendsSince = Timestamp.now(),
                accepted = true
            )
        )
    }
}