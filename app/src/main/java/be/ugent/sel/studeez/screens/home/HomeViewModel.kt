package be.ugent.sel.studeez.screens.home

import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService
) : StudeezViewModel(logService) {


    fun onViewFriendsClick(open: (String) -> Unit) {
        open(StudeezDestinations.FRIENDS_OVERVIEW_SCREEN)
    }
}
