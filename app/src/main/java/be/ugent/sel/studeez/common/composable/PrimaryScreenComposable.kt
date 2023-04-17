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
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.drawer.Drawer
import be.ugent.sel.studeez.screens.navbar.NavigationBar
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PrimaryScreenTemplate(
    title: String,
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    action: @Composable RowScope.() -> Unit,
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
            },
            actions = action
        ) },

        drawerContent = {
            Drawer(open, openAndPopUp)
        },

        bottomBar = { NavigationBar(open) },
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
        PrimaryScreenTemplate(
            "Preview screen",
            { _ -> {}},
            { _, _ -> {}},
            { IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }}
        ) {}
    }
}