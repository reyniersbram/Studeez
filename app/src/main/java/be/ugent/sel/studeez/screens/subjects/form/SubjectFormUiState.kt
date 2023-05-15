package be.ugent.sel.studeez.screens.subjects.form

import androidx.compose.ui.graphics.Color
import be.ugent.sel.studeez.common.ext.generateRandomArgb

data class SubjectFormUiState(
    val name: String = "",
    val color: Long = Color.generateRandomArgb(),
)