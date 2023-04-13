package be.ugent.sel.studeez.screens.templates.primary_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.CollapsedAddButton
import be.ugent.sel.studeez.common.composable.Drawer
import be.ugent.sel.studeez.common.composable.NavigationBar
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrimaryScreen(
    title: String,
    onLogoutClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,

        topBar = { TopAppBar(
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
            }
        ) },

        drawerContent = {
            Drawer(
                onLogoutClick = { onLogoutClick() }
            )
        },

        bottomBar = { NavigationBar() }, // TODO Pass arguments so that the current tab etc can be shown
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = { CollapsedAddButton() }
    ) {
        content(it)
    }
}

@Preview
@Composable
fun PrimaryScreenPreview() {
    StudeezTheme {
        PrimaryScreen(
            "Preview screen",
            {}
        ) {}
    }
}