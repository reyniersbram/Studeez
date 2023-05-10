package be.ugent.sel.studeez.screens.subjects.form

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import be.ugent.sel.studeez.data.SelectedSubject
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.SubjectDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

abstract class SubjectFormViewModel(
    protected val subjectDAO: SubjectDAO,
    protected val selectedSubject: SelectedSubject,
    logService: LogService,
) : StudeezViewModel(logService) {
    abstract val uiState: MutableState<SubjectFormUiState>

    protected val name: String
        get() = uiState.value.name

    protected val color: Long
        get() = uiState.value.color

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onColorChange(newValue: Long) {
        uiState.value = uiState.value.copy(color = newValue)
    }
}

@HiltViewModel
class SubjectCreateFormViewModel @Inject constructor(
    subjectDAO: SubjectDAO,
    selectedSubject: SelectedSubject,
    logService: LogService,
) : SubjectFormViewModel(subjectDAO, selectedSubject, logService) {
    override val uiState = mutableStateOf(SubjectFormUiState())

    fun onCreate(openAndPopUp: (String, String) -> Unit) {
        val random = Random
        val mask: Long = (0x000000FFL shl random.nextInt(0, 3)).inv()
        val randomColor = random.nextLong(0xFF000000L, 0xFFFFFFFFL) and mask
        val newSubject = Subject(
            name = name,
            argb_color = randomColor,
        )
        subjectDAO.saveSubject(
            newSubject
        )
        // TODO open newly created subject
//        selectedSubject.set(newSubject)
//        open(StudeezDestinations.TASKS_SCREEN)
        openAndPopUp(StudeezDestinations.SUBJECT_SCREEN, StudeezDestinations.ADD_SUBJECT_FORM)
    }
}

@HiltViewModel
class SubjectEditFormViewModel @Inject constructor(
    subjectDAO: SubjectDAO,
    selectedSubject: SelectedSubject,
    logService: LogService,
) : SubjectFormViewModel(subjectDAO, selectedSubject, logService) {
    override val uiState = mutableStateOf(
        SubjectFormUiState(
            name = selectedSubject().name,
            color = selectedSubject().argb_color
        )
    )

    fun onDelete(openAndPopUp: (String, String) -> Unit) {
        subjectDAO.updateSubject(selectedSubject().copy(archived = true))
        openAndPopUp(StudeezDestinations.SUBJECT_SCREEN, StudeezDestinations.EDIT_SUBJECT_FORM)
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