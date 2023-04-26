package be.ugent.sel.studeez.screens.timer_overview.add_timer

import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTimerViewModel @Inject constructor(
    logService: LogService
): StudeezViewModel(logService) {

}
