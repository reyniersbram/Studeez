package be.ugent.sel.studeez.screens.profile

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    logService: LogService
) : StudeezViewModel(logService) {

    var uiState = mutableStateOf(ProfileEditUiState())
        private set

    private val username
        get() = uiState.value.username

    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onSaveClick() {
        // TODO
    }

    fun onDeleteClick() {
        // TODO
    }
}