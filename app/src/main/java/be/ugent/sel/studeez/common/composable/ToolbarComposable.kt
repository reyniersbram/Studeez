package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.screens.drawer.Drawer
import be.ugent.sel.studeez.screens.drawer.StudeezDrawerState
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import kotlinx.coroutines.CoroutineScope

// TODO Add option for button in top right corner as extra button

@Composable
// Contains floatingActionButton and bottom bar, used in the main four screens.
fun PrimaryScreenToolbar(
    title: String,
    openAndPopUp: (String, String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val primaryScreenToolbarState = rememberPrimaryScreenToolbarState()

    Scaffold(
        scaffoldState = primaryScreenToolbarState.scaffoldState,

        // Everything at the top of the screen
        topBar = { TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { primaryScreenToolbarState.openDrawer() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        ) },

        // Drawer
        drawerContent = @Composable {
            Drawer(openAndPopUp)
        },

        // Everything at the bottom of the screen
        bottomBar = { NavigationBar() }, // TODO Pass arguments so that the current tab etc can be shown
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = { CollapsedAddButton() }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
fun rememberPrimaryScreenToolbarState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState, coroutineScope) {
    StudeezDrawerState(scaffoldState, coroutineScope)
}

@Preview
@Composable
fun PrimaryScreenToolbarPreview() {
    StudeezTheme { PrimaryScreenToolbar(
        "Preview screen",
        { a, b -> {}}
    ) {} }
}

@Composable
// Does not contain floatingActionButton and bottom bar, used in all the other screens
fun SecondaryScreenToolbar(
    title: String,
    popUp: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        // Everything at the top of the screen
        topBar = { TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { /* TODO Go back */ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go back")
                }
            }
        ) },
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Preview
@Composable
fun PrimaryScreenToolbarPreview() {
    StudeezTheme { PrimaryScreenToolbar(
        "Preview screen",
        {}
    ) {} }
}

@Preview
@Composable
fun SecondaryScreenToolbarPreview() {
    StudeezTheme { SecondaryScreenToolbar(
        "Preview screen"
    ) {} }
}