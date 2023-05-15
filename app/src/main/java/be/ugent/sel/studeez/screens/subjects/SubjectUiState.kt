package be.ugent.sel.studeez.screens.subjects

import be.ugent.sel.studeez.data.local.models.task.Subject

sealed interface SubjectUiState {
    object Loading : SubjectUiState
    data class Succes(val subjects: List<Subject>) : SubjectUiState
}