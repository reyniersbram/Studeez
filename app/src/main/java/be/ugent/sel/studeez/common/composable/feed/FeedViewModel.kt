package be.ugent.sel.studeez.common.composable.feed

import androidx.lifecycle.viewModelScope
import be.ugent.sel.studeez.data.SelectedTask
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.domain.FeedDAO
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.TaskDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    feedDAO: FeedDAO,
    private val taskDAO: TaskDAO,
    private val selectedTask: SelectedTask,
    logService: LogService
) : StudeezViewModel(logService) {

    private val entries: Flow<Map<String, List<FeedEntry>>> = feedDAO.getFeedEntries()

    val uiState: StateFlow<FeedUiState> = feedDAO.getFeedEntries()
        .map { FeedUiState.Succes(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = FeedUiState.Loading,
            started = SharingStarted.Eagerly,
        )

    fun getFeedEntries(): Flow<Map<String, List<FeedEntry>>> {
        return entries
    }

    fun continueTask(open: (String) -> Unit, subjectId: String, taskId: String) {
        viewModelScope.launch {
            val task = taskDAO.getTask(subjectId, taskId)
            selectedTask.set(task)
            open(StudeezDestinations.TIMER_SELECTION_SCREEN)
        }
    }
}