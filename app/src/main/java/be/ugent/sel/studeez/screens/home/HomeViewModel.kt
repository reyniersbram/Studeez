package be.ugent.sel.studeez.screens.home

import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(logService: LogService) : StudeezViewModel(logService) {

    fun onStartSessionClick(openAndPopUp: (String, String) -> Unit) {
        //openAndPopUp(StudeezDestinations.xxx, StudeezDestinations.HOME_SCREEN)
    }
}