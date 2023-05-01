package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.drawer.Drawer
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBar
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrimaryScreenTemplate(
    title: String,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    barAction: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,

        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch { scaffoldState.drawerState.open() }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = resources().getString(R.string.menu)
                        )
                    }
                },
                actions = barAction
            )
        },

        drawerContent = {
            Drawer(drawerActions)
        },

        bottomBar = { NavigationBar(navigationBarActions) },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = { AddButton(AddButtonActions(
            onTaskClick = navigationBarActions.onAddTaskClick,
            onFriendClick = navigationBarActions.onAddFriendClick,
            onSessionClick = navigationBarActions.onAddSessionClick
        )) }
    ) {
        content(it)
    }
}

@Preview
@Composable
fun PrimaryScreenPreview() {
    StudeezTheme {
        PrimaryScreenTemplate(
            "Preview screen",
            DrawerActions({}, {}, {}, {}, {}),
            NavigationBarActions({ false }, {}, {}, {}, {}, {}, {}, {}),
            {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            },
        ) {}
    }
}