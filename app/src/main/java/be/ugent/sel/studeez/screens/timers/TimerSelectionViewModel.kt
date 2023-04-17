package be.ugent.sel.studeez.screens.timers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TimerDAO
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TimerSelectionViewModel @Inject constructor(
    private val timerDAO: TimerDAO,
    logService: LogService
) : StudeezViewModel(logService) {

    var inSession = mutableStateOf(false)
    var sessionTimer: FunctionalTimer? = null

    fun getAllTimers() : Flow<List<TimerInfo>> {
        return timerDAO.getAllTimers()
    }

    fun startSession(timerInfo: TimerInfo) {
        inSession.value = true
        sessionTimer = timerInfo.getFunctionalTimer()
    }
}