package be.ugent.sel.studeez.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.templates.primary_screen.PrimaryScreen

@Composable
fun HomeScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    PrimaryScreen(
        title = resources().getString(R.string.home),
        onLogoutClick = { viewModel.onLogoutClick(openAndPopUp) }
    ) {
        BasicButton(R.string.start_session, Modifier.basicButton()) {
            viewModel.onStartSessionClick(openAndPopUp)
        }
    }
}