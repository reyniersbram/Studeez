package be.ugent.sel.studeez.screens.profile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun ProfileScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    PrimaryScreenTemplate(
        title = resources().getString(AppText.profile),
        open = open,
        openAndPopUp = openAndPopUp
    ) {
        Text(text = "This is your profile!") // TODO
    }
}