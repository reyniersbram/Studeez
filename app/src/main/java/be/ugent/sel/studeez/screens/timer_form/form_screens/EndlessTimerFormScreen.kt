package be.ugent.sel.studeez.screens.timer_form.form_screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.data.local.models.timer_info.EndlessTimerInfo
import be.ugent.sel.studeez.ui.theme.StudeezTheme

class EndlessTimerFormScreen(
    endlessTimerInfo: EndlessTimerInfo
): AbstractTimerFormScreen(endlessTimerInfo) {
}

@Preview
@Composable
fun EndlessEditScreenPreview() {
    val endlessTimerInfo = EndlessTimerInfo(
        "Forever and beyond",
        "My endless timer description",
    )
    StudeezTheme {
        EndlessTimerFormScreen(endlessTimerInfo).invoke(onSaveClick = {}, {})
    }
}