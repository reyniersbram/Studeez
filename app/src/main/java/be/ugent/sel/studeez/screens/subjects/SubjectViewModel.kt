package be.ugent.sel.studeez.screens.subjects

import androidx.lifecycle.viewModelScope
import be.ugent.sel.studeez.data.SelectedSubject
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.SubjectDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectDAO: SubjectDAO,
    private val selectedSubject: SelectedSubject,
    logService: LogService,
) : StudeezViewModel(logService) {

    val uiState: StateFlow<SubjectUiState> = subjectDAO.getSubjects()
        .map { SubjectUiState.Succes(it) }
        .stateIn(
            scope = viewModelScope,
            initialValue = SubjectUiState.Loading,
            started = SharingStarted.Eagerly,
        )

    fun onAddSubject(open: (String) -> Unit) {
        open(StudeezDestinations.ADD_SUBJECT_FORM)
    }

    fun getTaskCount(subject: Subject): Flow<Int> {
        return subjectDAO.getTaskCount(subject)
    }

    fun getCompletedTaskCount(subject: Subject): Flow<Int> {
        return subjectDAO.getCompletedTaskCount(subject)
    }

    fun getStudyTime(subject: Subject): Flow<Int> {
        return subjectDAO.getStudyTime(subject)
    }

    fun onViewSubject(subject: Subject, open: (String) -> Unit) {
        selectedSubject.set(subject)
        open(StudeezDestinations.TASKS_SCREEN)
    }
}