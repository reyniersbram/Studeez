package be.ugent.sel.studeez.screens.timer_selection

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.SelectedTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
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
    private val selectedTimer: SelectedTimer,
    logService: LogService
) : StudeezViewModel(logService) {

    var customTimerStudyTime: MutableState<Int> = mutableStateOf(
        HoursMinutesSeconds(1, 0, 0).getTotalSeconds()
    )

    fun getAllTimers(): Flow<List<TimerInfo>> {
        return timerDAO.getAllTimers()
    }

    fun startSession(open: (String) -> Unit, timerInfo: TimerInfo) {
        selectedTimer.set(timerInfo.getFunctionalTimer())
        open(StudeezDestinations.SESSION_SCREEN)
    }
}