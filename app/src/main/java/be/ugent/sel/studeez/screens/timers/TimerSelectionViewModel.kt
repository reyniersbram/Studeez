package be.ugent.sel.studeez.screens.timers

import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TimerDAO
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TimerSelectViewModel @Inject constructor(
    private val timerDAO: TimerDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    fun getAllTimers() : Flow<List<TimerInfo>> {
        return timerDAO.getAllTimers()
    }

    fun startSession() {

    }
}