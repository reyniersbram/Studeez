package be.ugent.sel.studeez.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicTextButton
import be.ugent.sel.studeez.common.composable.LabelledInputField
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.ext.textButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme

@Composable
fun EditProfileScreen(
    goBack: () -> Unit,
    viewModel: ProfileEditViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    SecondaryScreenTemplate(
        title = resources().getString(R.string.editing_profile),
        popUp = goBack
    ) {
        Column {
            LabelledInputField(
                value = uiState.username,
                onNewValue = viewModel::onUsernameChange,
                label = R.string.username
            )

            BasicTextButton(text = R.string.save, Modifier.textButton()) {
                viewModel.onSaveClick()
            }
            BasicTextButton(text = R.string.delete_profile, Modifier.textButton()) {
                viewModel.onDeleteClick()
            }
        }
    }
}

@Preview
@Composable
fun EditProfileScreenComposable() {
    StudeezTheme {
        EditProfileScreen (
            {}
        )
    }
}