package be.ugent.sel.studeez.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.resources

@Composable
fun HomeScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    PrimaryScreenTemplate(
        title = resources().getString(R.string.home),
        open = open,
        openAndPopUp = openAndPopUp
    ) {
        BasicButton(R.string.start_session, Modifier.basicButton()) {
            viewModel.onStartSessionClick(open)
        }
    }
}