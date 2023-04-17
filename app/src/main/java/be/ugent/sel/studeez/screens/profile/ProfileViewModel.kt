package be.ugent.sel.studeez.screens.profile

import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewModelScope
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.UserDAO
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDAO: UserDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    suspend fun getUsername(): String? {
        return userDAO.getUsername()
    }

}