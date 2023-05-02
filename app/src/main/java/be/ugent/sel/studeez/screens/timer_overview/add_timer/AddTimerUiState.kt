package be.ugent.sel.studeez.screens.timer_overview.add_timer

data class AddTimerUiState(
    val studyTimeHours: Int = 1,
    val studyTimeMinutes: Int = 0,
    val withBreaks: Boolean = false,
    val breakTimeMinutes: Int = 5,
    val breakTimeHours: Int = 0,
    val repeats: Int = 1,
    val name: String = "Timer",
    val description: String = "Long study session",
)