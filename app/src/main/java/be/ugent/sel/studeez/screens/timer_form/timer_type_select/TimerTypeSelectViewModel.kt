package be.ugent.sel.studeez.screens.timer_form.timer_add

import be.ugent.sel.studeez.data.EditTimerState
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TimerDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TimerTypeSelectViewModel @Inject constructor(
    private val editTimerState: EditTimerState,
    logService: LogService
) : StudeezViewModel(logService) {


    fun onTimerTypeChosen(timerInfo: TimerInfo, open: (String) -> Unit) {
        editTimerState.timerInfo = timerInfo
        open(StudeezDestinations.ADD_TIMER_SCREEN)
    }
}