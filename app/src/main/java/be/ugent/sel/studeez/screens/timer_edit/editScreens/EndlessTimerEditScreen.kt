package be.ugent.sel.studeez.screens.timer_edit.editScreens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.data.local.models.timer_info.EndlessTimerInfo
import be.ugent.sel.studeez.ui.theme.StudeezTheme

class EndlessTimerEditScreen(
    endlessTimerInfo: EndlessTimerInfo
): AbstractTimerEditScreen(endlessTimerInfo) {
}

@Preview
@Composable
fun EndlessEditScreenPreview() {
    val endlessTimerInfo = EndlessTimerInfo(
        "Forever and beyond",
        "My endless timer description",
    )
    StudeezTheme {
        EndlessTimerEditScreen(endlessTimerInfo).invoke(onSaveClick = {})
    }
}