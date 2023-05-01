package be.ugent.sel.studeez.screens.timer_edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.screens.timer_edit.editScreens.AbstractTimerEditScreen
import be.ugent.sel.studeez.ui.theme.StudeezTheme

data class TimerEditActions(
    val getTimerInfo: () -> TimerInfo,
    val saveTimer: (TimerInfo) -> Unit
)

fun getTimerEditActions(
    viewModel: TimerEditViewModel,
    open: (String) -> Unit
): TimerEditActions {
    return TimerEditActions(
        getTimerInfo = viewModel::getTimerInfo,
        saveTimer = viewModel::saveTimer
    )
}

@Composable
fun TimerEditRoute(
    open: (String) -> Unit,
    popUp: () -> Unit,
    viewModel: TimerEditViewModel,
) {
    TimerEditScreen(
        timerEditActions = getTimerEditActions(viewModel, open),
        popUp = popUp
    )
}

@Composable
fun TimerEditScreen(
    timerEditActions: TimerEditActions,
    popUp: () -> Unit
) {
    SecondaryScreenTemplate(title = "Edit Timer", popUp = { /*TODO*/ }) {
        val timerEditScreen = timerEditActions.getTimerInfo().accept(GetTimerEditScreen())
        timerEditScreen { timerInfo -> timerEditActions.saveTimer(timerInfo) }
    }
}

//@Preview
//@Composable
//fun TimerEditScreenPreview() {
//    val editEntries: List<EditEntry> = listOf(
//        EditEntry("Name", "MyTimer") {},
//        EditEntry("Description", "Dit is een leuke timer") {},
//        EditEntry("StudyTime", "25") {}
//    )
//    val actions = TimerEditActions { editEntries }
//    StudeezTheme { TimerEditScreen(timerEditActions = actions) {} }
//}
























