package be.ugent.sel.studeez.screens.tasks

import be.ugent.sel.studeez.data.SelectedSubject
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.SubjectDAO
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectDAO: SubjectDAO,
    private val selectedSubject: SelectedSubject,
    logService: LogService,
) : StudeezViewModel(logService) {
    fun addSubject(open: (String) -> Unit) {
        open(StudeezDestinations.ADD_SUBJECT_FORM)
    }

    fun getSubjects(): Flow<List<Subject>> {
        return subjectDAO.getSubjects()
    }

    fun onViewSubject(subject: Subject, open: (String) -> Unit) {
        selectedSubject.set(subject)
        open(StudeezDestinations.TASKS_SCREEN)
    }
}