package be.ugent.sel.studeez.screens.timer_edit

import be.ugent.sel.studeez.data.EditTimerState
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TimerDAO
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerEditViewModel @Inject constructor(
    private val editTimerState: EditTimerState,
    private val timerDAO: TimerDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    private val timerInfo: TimerInfo = editTimerState.timerInfo

    fun getTimerInfo(): TimerInfo {
        return timerInfo
    }

    fun editTimer(timerInfo: TimerInfo, goBack: () -> Unit) {
        timerDAO.updateTimer(timerInfo)
        goBack()
    }

    fun saveTimer(timerInfo: TimerInfo, goBack: () -> Unit) {
        timerDAO.saveTimer(timerInfo)
        goBack()
    }
}