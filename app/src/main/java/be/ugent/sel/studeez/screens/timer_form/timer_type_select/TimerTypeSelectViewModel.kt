package be.ugent.sel.studeez.screens.timer_form.timer_type_select

import be.ugent.sel.studeez.data.SelectedTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerTypeSelectViewModel @Inject constructor(
    private val selectedTimerInfo: SelectedTimerInfo,
    logService: LogService
) : StudeezViewModel(logService) {


    fun onTimerTypeChosen(timerInfo: TimerInfo, open: (String) -> Unit) {
        selectedTimerInfo.set(timerInfo)
        open(StudeezDestinations.ADD_TIMER_SCREEN)
    }
}