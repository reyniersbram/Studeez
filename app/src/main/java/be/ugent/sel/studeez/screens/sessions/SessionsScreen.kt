package be.ugent.sel.studeez.screens.sessions

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun SessionsRoute(
    // viewModel: SessionsViewModel,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions
) {
    SessionsScreen(
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions
    )
}

@Composable
fun SessionsScreen(
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions
) {
    PrimaryScreenTemplate(
        title = resources().getString(AppText.upcoming_sessions),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions
    ) {
        Text(
            text = resources().getString(AppText.sessions_temp_description),
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}