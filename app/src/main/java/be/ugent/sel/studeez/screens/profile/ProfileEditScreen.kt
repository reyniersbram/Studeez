package be.ugent.sel.studeez.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
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

            BasicTextButton(text = R.string.save, Modifier.textButton()) {} // TODO
            BasicTextButton(text = R.string.delete_profile, Modifier.textButton()) {} // TODO
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