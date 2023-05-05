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
    var uiState = mutableStateOf(
        if (selectedSubject.isSet()) SubjectFormUiState(
            name = selectedSubject().name,
            color = selectedSubject().argb_color
        )
        else SubjectFormUiState()
    )
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

    fun onDelete(openAndPopUp: (String, String) -> Unit) {
        subjectDAO.deleteSubject(selectedSubject())
        openAndPopUp(StudeezDestinations.SUBJECT_SCREEN, StudeezDestinations.EDIT_SUBJECT_FORM)
    }

    fun onCreate(openAndPopUp: (String, String) -> Unit) {
        val newSubject = Subject(
            name = name,
            argb_color = color,
        )
        subjectDAO.saveSubject(
            newSubject
        )
        // TODO open newly created subject
//        selectedSubject.set(newSubject)
//        open(StudeezDestinations.TASKS_SCREEN)
        openAndPopUp(StudeezDestinations.SUBJECT_SCREEN, StudeezDestinations.ADD_SUBJECT_FORM)
    }

    fun onEdit(openAndPopUp: (String, String) -> Unit) {
        val newSubject = selectedSubject().copy(
            name = name,
            argb_color = color,
        )
        subjectDAO.updateSubject(newSubject)
        openAndPopUp(StudeezDestinations.TASKS_SCREEN, StudeezDestinations.EDIT_SUBJECT_FORM)
    }
}