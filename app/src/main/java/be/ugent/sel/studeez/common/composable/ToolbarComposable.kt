package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.ui.theme.StudeezTheme

// TODO Add option for button in top right corner as extra button

@Composable
// Contains floatingActionButton and bottom bar, used in the main four screens.
fun PrimaryScreenToolbar(
    title: String,
    openDrawer: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        // Everything at the top of the screen
        topBar = { TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { openDrawer() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        ) },

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
// Does not contain floatingActionButton and bottom bar, used in all the other screens
fun SecondaryScreenToolbar(
    title: String,
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