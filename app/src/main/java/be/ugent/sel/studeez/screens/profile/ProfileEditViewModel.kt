package be.ugent.sel.studeez.screens.profile

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.domain.AccountDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val accountDAO: AccountDAO,
    private val userDAO: UserDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    var uiState = mutableStateOf(ProfileEditUiState())
        private set

    init {
        launchCatching {
            val user: User = userDAO.getLoggedInUser()
            uiState.value = uiState.value.copy(
                username = user.username,
                biography = user.biography
            )
        }
    }

    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onBiographyChange(newValue: String) {
        uiState.value = uiState.value.copy(biography = newValue)
    }

    fun onSaveClick() {
        launchCatching {
            userDAO.saveLoggedInUser(
                newUsername = uiState.value.username,
                newBiography = uiState.value.biography
            )
            SnackbarManager.showMessage(R.string.success)
        }
    }

    fun onDeleteClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            userDAO.deleteLoggedInUserReferences() // Delete references
            accountDAO.deleteAccount() // Delete authentication
        }
        openAndPopUp(StudeezDestinations.SIGN_UP_SCREEN, StudeezDestinations.EDIT_PROFILE_SCREEN)
    }
}