package be.ugent.sel.studeez.screens.profile.edit_profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.common.composable.BasicTextButton
import be.ugent.sel.studeez.common.composable.LabelledInputField
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.ext.textButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import be.ugent.sel.studeez.R.string as AppText

data class EditProfileActions(
    val onUserNameChange: (String) -> Unit,
    val onBiographyChange: (String) -> Unit,
    val onSaveClick: () -> Unit,
    val onDeleteClick: () -> Unit
)

fun getEditProfileActions(
    viewModel: ProfileEditViewModel,
    openAndPopUp: (String, String) -> Unit,
): EditProfileActions {
    return EditProfileActions(
        onUserNameChange = { viewModel.onUsernameChange(it) },
        onBiographyChange = { viewModel.onBiographyChange(it) },
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
        title = resources().getString(AppText.editing_profile),
        popUp = goBack
    ) {
        LazyColumn {
            item {
                LabelledInputField(
                    value = uiState.username,
                    onNewValue = editProfileActions.onUserNameChange,
                    label = AppText.username
                )
            }
            item {
                LabelledInputField(
                    value = uiState.biography,
                    onNewValue = editProfileActions.onBiographyChange,
                    label = AppText.biography
                )
            }
            item {
                BasicTextButton(
                    text = AppText.save,
                    Modifier.textButton(),
                    action = {
                        editProfileActions.onSaveClick()
                        goBack()
                    }
                )
            }
            item {
                BasicTextButton(
                    text = AppText.delete_profile,
                    Modifier.textButton(),
                    action = editProfileActions.onDeleteClick
                )
            }
        }
    }
}

@Preview
@Composable
fun EditProfileScreenComposable() {
    StudeezTheme {
        EditProfileScreen({}, ProfileEditUiState(), EditProfileActions({}, {}, {}, {}))
    }
}