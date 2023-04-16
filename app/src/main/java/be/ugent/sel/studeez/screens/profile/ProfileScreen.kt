package be.ugent.sel.studeez.screens.profile

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.Headline
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun ProfileScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var username: String? by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        username = viewModel.getUsername()
    }

    PrimaryScreenTemplate(
        title = resources().getString(AppText.profile),
        open = open,
        openAndPopUp = openAndPopUp
    ) {
        Headline(text = (username ?: resources().getString(R.string.no_username)))
    }
}