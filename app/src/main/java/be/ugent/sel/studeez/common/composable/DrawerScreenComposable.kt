package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import be.ugent.sel.studeez.common.composable.drawer.Drawer
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun DrawerScreenTemplate(
    title: String,
    drawerActions: DrawerActions,
    action: @Composable RowScope.() -> Unit = {},
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
                        contentDescription = resources().getString(AppText.menu)
                    )
                }
            },
            actions = action
        )},

        drawerContent = {
            Drawer(drawerActions)
        }
    ) {
        content(it)
    }
}