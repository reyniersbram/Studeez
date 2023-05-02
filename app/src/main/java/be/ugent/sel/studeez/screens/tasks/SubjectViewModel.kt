package be.ugent.sel.studeez.screens.tasks

import android.util.Log
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
    fun addSubject() {
        subjectDAO.saveSubject(
            Subject(
                name = "Test Subject",
                time = 0,
                argb_color = 0xFFF44336,
            )
        )
    }

    fun getSubjects(): Flow<List<Subject>> {
        return subjectDAO.getSubjects()
    }

    fun onViewSubject(subject: Subject, open: (String) -> Unit) {
        Log.v("MYLOG", subject.id)
        selectedSubject.set(subject)
        open(StudeezDestinations.TASKS_SCREEN)
    }
}