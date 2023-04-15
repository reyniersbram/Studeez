package be.ugent.sel.studeez.screens.profile

import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    logService: LogService
) : StudeezViewModel(logService) {

}