package be.ugent.sel.studeez.screens.timers

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.SelectedTimerRepo
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TimerDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TimerSelectionViewModel @Inject constructor(
    private val timerDAO: TimerDAO,
    private val selectedTimerRepo: SelectedTimerRepo,
    logService: LogService
) : StudeezViewModel(logService) {

    fun getAllTimers() : Flow<List<TimerInfo>> {
        return timerDAO.getAllTimers()
    }

    fun startSession(open: (String) -> Unit, timerInfo: TimerInfo) {
        selectedTimerRepo.selectedTimer = timerInfo.getFunctionalTimer()
        open(StudeezDestinations.SESSION_SCREEN)
    }
}