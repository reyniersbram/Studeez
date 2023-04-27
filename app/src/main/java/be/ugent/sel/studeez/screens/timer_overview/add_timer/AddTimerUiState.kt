package be.ugent.sel.studeez.screens.timer_overview.add_timer

data class AddTimerUiState(
    val studyTimeHours: Float = 1f,
    val studyTimeMinutes: Float = 0f,
    val withBreaks: Boolean = false,
    val breakTime: Float = 5f,
    val repeats: Float = 1f,
    val name: String = "",
    val description: String = "",
)