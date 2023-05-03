package be.ugent.sel.studeez.screens.tasks.forms

import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.SelectedSubject
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.SubjectDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubjectFormViewModel @Inject constructor(
    private val subjectDAO: SubjectDAO,
    private val selectedSubject: SelectedSubject,
    logService: LogService,
) : StudeezViewModel(logService) {
    var uiState = mutableStateOf(SubjectFormUiState())
        private set

    private val name: String
        get() = uiState.value.name

    private val color: Long
        get() = uiState.value.color

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onColorChange(newValue: Long) {
        uiState.value = uiState.value.copy(color = newValue)
    }

    fun onDelete(open: (String) -> Unit) {
        subjectDAO.deleteSubject(selectedSubject())
        open(StudeezDestinations.SUBJECT_SCREEN)
    }

    fun onCreate(open: (String) -> Unit) {
        val newSubject = Subject(
            name = name,
            argb_color = color,
        )
        subjectDAO.saveSubject(
            newSubject
        )
        selectedSubject.set(newSubject)
        // TODO open newly created subject
//        open(StudeezDestinations.TASKS_SCREEN)
        open(StudeezDestinations.SUBJECT_SCREEN)
    }

    fun onEdit(open: (String) -> Unit) {
        val newSubject = selectedSubject().copy(
            name = name,
            argb_color = color,
        )
        subjectDAO.updateSubject(
            newSubject
        )
        open(StudeezDestinations.TASKS_SCREEN)
    }
}