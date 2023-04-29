package be.ugent.sel.studeez.screens.tasks

import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.domain.LogService
import be.ugent.sel.studeez.domain.SubjectDAO
import be.ugent.sel.studeez.screens.StudeezViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val subjectDAO: SubjectDAO,
    logService: LogService,
) : StudeezViewModel(logService) {
    fun addSubject() {
        subjectDAO.saveSubject(
            Subject(
                name = "Test Subject",
                tasks = listOf(),
                time = 0,
                color = 0,
            )
        )
    }

    fun getSubjects(): Flow<List<Subject>> {
        return subjectDAO.getSubjects()
    }
}