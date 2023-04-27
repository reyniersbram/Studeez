package be.ugent.sel.studeez.screens.timer_overview.add_timer

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.PomodoroTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TimerDAO
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTimerViewModel @Inject constructor(
    logService: LogService,
    private val timerDAO: TimerDAO,
): StudeezViewModel(logService) {
    var uiState = mutableStateOf(AddTimerUiState())
        private set

    private val studyTimeHours
        get() = uiState.value.studyTimeHours

    private val studyTimeMinutes
        get() = uiState.value.studyTimeMinutes

    private val breakTime
        get() = uiState.value.breakTime

    private val repeats
        get() = uiState.value.repeats

    private val name
        get() = uiState.value.name

    private val description
        get() = uiState.value.description

    fun onStudyTimeHoursChange(newValue: Float) {
        uiState.value = uiState.value.copy(studyTimeHours = newValue)

    }

    fun onStudyTimeMinutesChange(newValue: Float) {
        uiState.value = uiState.value.copy(studyTimeMinutes = newValue)
    }

    fun onWithBreaksChange(newValue: Boolean) {
        uiState.value = uiState.value.copy(withBreaks = newValue)
    }

    fun onBreakTimeChange(newValue: Float) {
        uiState.value = uiState.value.copy(breakTime = newValue)
    }

    fun onRepeatsChange(newValue: Float) {
        uiState.value = uiState.value.copy(repeats = newValue)
    }

    fun addTimer() {
        if (uiState.value.withBreaks) {
            timerDAO.saveTimer(PomodoroTimerInfo(
                name = uiState.value.name,
                description = uiState.value.description,
                studyTime = studyTimeHours.toInt() * 60 * 60 + studyTimeMinutes.toInt() * 60,
                breakTime = breakTime.toInt() * 60,
                repeats = repeats.toInt()
            ))
        } else {
            timerDAO.saveTimer(CustomTimerInfo(
                name = uiState.value.name,
                description = uiState.value.description,
                studyTime = studyTimeHours.toInt() * 60 * 60 + studyTimeMinutes.toInt() * 60
            ))
        }
    }

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(description = newValue)
    }
}
