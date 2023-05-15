package be.ugent.sel.studeez.screens.timer_form

import be.ugent.sel.studeez.data.SelectedTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TimerDAO
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerFormViewModel @Inject constructor(
    private val selectedTimerInfo: SelectedTimerInfo,
    private val timerDAO: TimerDAO,
    logService: LogService
) : StudeezViewModel(logService) {
    fun getTimerInfo(): TimerInfo {
        return selectedTimerInfo()
    }

    fun editTimer(timerInfo: TimerInfo, goBack: () -> Unit) {
        timerDAO.updateTimer(timerInfo)
        goBack()
    }

    fun deleteTimer(timerInfo: TimerInfo, goBack: () -> Unit) {
        timerDAO.deleteTimer(timerInfo)
        goBack()
    }

    fun saveTimer(timerInfo: TimerInfo, goBack: () -> Unit) {
        timerDAO.saveTimer(timerInfo)
        goBack()
    }
}