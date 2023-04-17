package be.ugent.sel.studeez.screens.profile

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
        openAndPopUp = openAndPopUp,
        action = { EditAction { viewModel.onEditProfileClick(open) } }
    ) {
        Headline(text = (username ?: resources().getString(R.string.no_username)))
    }
}

@Composable
fun EditAction(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = resources().getString(AppText.edit_profile)
        )

    }
}