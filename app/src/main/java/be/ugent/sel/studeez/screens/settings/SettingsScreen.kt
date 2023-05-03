package be.ugent.sel.studeez.screens.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import be.ugent.sel.studeez.common.composable.DrawerScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun SettingsRoute(
    // viewModel: SettingsViewModel,
    drawerActions: DrawerActions
) {
    SettingsScreen(
        drawerActions = drawerActions
    )
}

@Composable
fun SettingsScreen(
    drawerActions: DrawerActions
) {
    DrawerScreenTemplate(
        title = resources().getString(AppText.settings),
        drawerActions = drawerActions
    ) {
        Text(
            text = resources().getString(AppText.settings_temp_description),
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}