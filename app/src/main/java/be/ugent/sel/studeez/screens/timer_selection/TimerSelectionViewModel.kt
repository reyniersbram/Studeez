package be.ugent.sel.studeez.screens.timer_selection

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import be.ugent.sel.studeez.data.SelectedTimerState
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
    private val selectedTimerState: SelectedTimerState,
    logService: LogService
) : StudeezViewModel(logService) {

    var customTimerStudyTime: MutableState<Int> = mutableStateOf(
        HoursMinutesSeconds(1, 0, 0).getTotalSeconds()
    )

    fun getAllTimers() : Flow<List<TimerInfo>> {
        return timerDAO.getAllTimers()
    }

    fun startSession(open: (String) -> Unit, timerInfo: TimerInfo) {
        selectedTimerState.selectedTimer = timerInfo.getFunctionalTimer()
        open(StudeezDestinations.SESSION_SCREEN)
    }
}