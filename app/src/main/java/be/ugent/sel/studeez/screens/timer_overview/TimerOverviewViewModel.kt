package be.ugent.sel.studeez.screens.timer_overview

import be.ugent.sel.studeez.data.EditTimerState
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.ConfigurationService
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TimerDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TimerOverviewViewModel @Inject constructor(
    private val configurationService: ConfigurationService,
    private val timerDAO: TimerDAO,
    private val editTimerState: EditTimerState,
    logService: LogService
) : StudeezViewModel(logService) {

    fun getUserTimers() : Flow<List<TimerInfo>> {
        return timerDAO.getUserTimers()
    }

    fun getDefaultTimers(): List<TimerInfo> {
        return configurationService.getDefaultTimers()
    }

    fun update(timerInfo: TimerInfo, open: (String) -> Unit)  {
        editTimerState.timerInfo = timerInfo
        open(StudeezDestinations.TIMER_EDIT_SCREEN)
    }

    fun onAddClick(open: (String) -> Unit) {
        open(StudeezDestinations.TIMER_TYPE_CHOOSING_SCREEN)
    }

    fun delete(timerInfo: TimerInfo) =timerDAO.deleteTimer(timerInfo)

    fun save(timerInfo: TimerInfo) = timerDAO.saveTimer(timerInfo)


}