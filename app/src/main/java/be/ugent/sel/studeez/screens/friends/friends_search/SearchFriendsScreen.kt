package be.ugent.sel.studeez.screens.friends.friends_search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import be.ugent.sel.studeez.common.composable.ProfilePicture
import be.ugent.sel.studeez.common.composable.drawer.DrawerEntry
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import be.ugent.sel.studeez.R.string as AppText

data class SearchFriendsActions(
    val onQueryStringChange: (String) -> Unit,
    val getUsersWithUsername: (String) -> Unit,
    val getAllUsers: () -> Flow<List<User>>,
    val goToProfile: (String) -> Unit
)

fun getSearchFriendsActions(
    viewModel: SearchFriendsViewModel,
    open: (String) -> Unit
): SearchFriendsActions {
    return SearchFriendsActions(
        onQueryStringChange = viewModel::onQueryStringChange,
        getUsersWithUsername = viewModel::getUsersWithUsername,
        getAllUsers = { viewModel.getAllUsers() },
        goToProfile = { userId -> viewModel.goToProfile(userId, open) }
    )
}

@Composable
fun SearchFriendsRoute(
    popUp: () -> Unit,
    open: (String) -> Unit,
    viewModel: SearchFriendsViewModel
) {
    val uiState by viewModel.uiState

    SearchFriendsScreen(
        popUp = popUp,
        uiState = uiState,
        searchFriendsActions = getSearchFriendsActions(
            viewModel = viewModel,
            open = open
        )
    )
}

@Composable
fun SearchFriendsScreen(
    popUp: () -> Unit,
    uiState: SearchFriendUiState,
    searchFriendsActions: SearchFriendsActions
) {
    var query by remember { mutableStateOf(uiState.queryString) }
    val searchResults = searchFriendsActions.getAllUsers().collectAsState(
        initial = emptyList()
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // TODO Make search field
//                    SearchField(
//                        value = uiState.queryString,
//                        onValueChange = friendsOverviewActions.onQueryStringChange,
//                        onSubmit = friendsOverviewActions.onSubmit,
//                        label = AppText.search_friends,
//                        enabled = false
//                    )
                    Text(
                        text = stringResource(id = AppText.searching_friends)
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
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(searchResults.value) { user ->
                UserEntry(
                    user = user,
                    goToProfile = searchFriendsActions.goToProfile
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchFriendsPreview() {
    StudeezTheme {
        SearchFriendsScreen(
            popUp = {},
            uiState = SearchFriendUiState(
                queryString = "dit is een test",
                searchResults = flowOf(
                    listOf(
                        User(
                            id = "someid",
                            username = "Eerste user",
                            biography = "blah blah blah"
                        )
                    )
                )
            ),
            searchFriendsActions = SearchFriendsActions(
                onQueryStringChange = {},
                getUsersWithUsername = {},
                getAllUsers = {
                    flowOf(
                        listOf(
                            User(
                                id = "someid",
                                username = "Eerste user",
                                biography = "blah blah blah"
                            )
                        )
                    )
                },
                goToProfile = { }
            )
        )
    }
}

@Composable
fun UserEntry(
    user: User,
    goToProfile: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 4.dp)
        ) {
            ProfilePicture()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
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
                    text = "${resources().getString(AppText.app_name)} ${
                        resources().getString(
                            AppText.friend
                        )
                    }",
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                SearchFriendsDropDown(
                    user = user,
                    goToProfile = goToProfile
                )
            }
        }
    }
}

@Preview
@Composable
fun UserEntryPreview() {
    StudeezTheme {
        UserEntry(
            user = User(
                id = "someid",
                username = "Eerste user",
                biography = "blah blah blah"
            ),
            goToProfile = { }
        )
    }
}

/**
 * Three dots that open a dropdown menu that allow to go the users profile.
 */
@Composable
fun SearchFriendsDropDown(
    user: User,
    goToProfile: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_more_horizontal),
            contentDescription = stringResource(AppText.view_more),
            modifier = Modifier.fillMaxSize()
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(onClick = { expanded = false }) {
            DrawerEntry(
                icon = Icons.Default.Person,
                text = stringResource(id = AppText.show_profile)
            ) {
                goToProfile(user.id)
            }
        }
    }
}

@Preview
@Composable
fun SearchFriendsDropDownPreview() {
    StudeezTheme {
        SearchFriendsDropDown(
            user = User(
                id = "someid",
                username = "Eerste user",
                biography = "blah blah blah"
            ),
            goToProfile = { }
        )
    }
}