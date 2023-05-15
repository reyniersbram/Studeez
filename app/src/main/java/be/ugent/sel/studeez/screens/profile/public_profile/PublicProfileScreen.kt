package be.ugent.sel.studeez.screens.profile.public_profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.Headline
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerEntry
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.profile.AmountOfFriendsButton
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import be.ugent.sel.studeez.R.string as AppText

data class PublicProfileActions(
    val getUserDetails: () -> Flow<User>,
    val getAmountOfFriends: () -> Flow<Int>,
    val onViewFriendsClick: () -> Unit,
    val sendFriendRequest: () -> Boolean
)

fun getPublicProfileActions(
    viewModel: PublicProfileViewModel,
    open: (String) -> Unit
): PublicProfileActions {
    return PublicProfileActions(
        getUserDetails = { viewModel.getUserDetails(viewModel.uiState.value.userId) },
        getAmountOfFriends = { viewModel.getAmountOfFriends(
            userId = viewModel.uiState.value.userId
        ) },
        onViewFriendsClick = { viewModel.onViewFriendsClick(open) },
        sendFriendRequest = { viewModel.sendFriendRequest(
            userId = viewModel.uiState.value.userId
        ) }
    )
}

@Composable
fun PublicProfileRoute(
    popUp: () -> Unit,
    open: (String) -> Unit,
    viewModel: PublicProfileViewModel
) {
    PublicProfileScreen(
        publicProfileActions = getPublicProfileActions(
            viewModel = viewModel,
            open = open
        ),
        popUp = popUp
    )
}

@Composable
fun PublicProfileScreen(
    publicProfileActions: PublicProfileActions,
    popUp: () -> Unit
) {
    val user = publicProfileActions.getUserDetails().collectAsState(initial = User())
    val amountOfFriends = publicProfileActions.getAmountOfFriends().collectAsState(initial = 0)

    SecondaryScreenTemplate(
        title = stringResource(id = AppText.profile),
        popUp = popUp,
        barAction = {
            PublicProfileEllipsis(
                sendFriendRequest = publicProfileActions.sendFriendRequest
            )
        }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Headline(text = user.value.username)
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                ) {
                    AmountOfFriendsButton(
                        amountOfFriends = amountOfFriends.value
                    ) {
                        publicProfileActions.onViewFriendsClick()
                    }
                }
            }

            item {
                Text(
                    text = user.value.biography,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(48.dp, 0.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PublicProfilePreview() {
    StudeezTheme {
        PublicProfileScreen(
            publicProfileActions = PublicProfileActions(
                getUserDetails = {
                    flowOf(User(
                        id = "someid",
                        username = "Maxime De Poorter",
                        biography = "I am a different student and this is my public profile"
                    ))
                },
                getAmountOfFriends = { flowOf(113) },
                onViewFriendsClick = {},
                sendFriendRequest = { true }
            ),
            popUp = {}
        )
    }
}

@Composable
fun PublicProfileEllipsis(
    sendFriendRequest: () -> Boolean
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
        DropdownMenuItem(onClick = { expanded = false }) {
            DrawerEntry(
                icon = Icons.Default.MailOutline,
                text = stringResource(id = AppText.send_friend_request)
            ) {
                sendFriendRequest()
            }
        }
    }
}

@Preview
@Composable
fun PublicProfileEllipsisPreview() {
    StudeezTheme {
        PublicProfileEllipsis(
            sendFriendRequest = { true }
        )
    }
}