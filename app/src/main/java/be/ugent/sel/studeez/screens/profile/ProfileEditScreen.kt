package be.ugent.sel.studeez.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicTextButton
import be.ugent.sel.studeez.common.composable.LabelledInputField
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.ext.textButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme

data class EditProfileActions(
    val onUserNameChange: (String) -> Unit,
    val onSaveClick: () -> Unit,
    val onDeleteClick: () -> Unit
)

fun getEditProfileActions(
    viewModel: ProfileEditViewModel,
    openAndPopUp: (String, String) -> Unit,
): EditProfileActions {
    return EditProfileActions(
        onUserNameChange = { viewModel.onUsernameChange(it) },
        onSaveClick = { viewModel.onSaveClick() },
        onDeleteClick = { viewModel.onDeleteClick(openAndPopUp) },
    )
}

@Composable
fun EditProfileRoute(
    goBack: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ProfileEditViewModel,
) {
    val uiState by viewModel.uiState
    EditProfileScreen(
        goBack = goBack,
        uiState = uiState,
        editProfileActions = getEditProfileActions(viewModel, openAndPopUp)
    )
}

@Composable
fun EditProfileScreen(
    goBack: () -> Unit,
    uiState: ProfileEditUiState,
    editProfileActions: EditProfileActions,
) {
    SecondaryScreenTemplate(
        title = resources().getString(R.string.editing_profile),
        popUp = goBack
    ) {
        Column {
            LabelledInputField(
                value = uiState.username,
                onNewValue = editProfileActions.onUserNameChange,
                label = R.string.username
            )
            BasicTextButton(
                text = R.string.save,
                Modifier.textButton(),
                action = {
                    editProfileActions.onSaveClick()
                    goBack()
                }
            )
            BasicTextButton(
                text = R.string.delete_profile,
                Modifier.textButton(),
                action = editProfileActions.onDeleteClick
            )
        }
    }
}

@Preview
@Composable
fun EditProfileScreenComposable() {
    StudeezTheme {
        EditProfileScreen({}, ProfileEditUiState(), EditProfileActions({}, {}, {}))
    }
}