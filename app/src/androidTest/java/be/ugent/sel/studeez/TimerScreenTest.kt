package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.data.local.models.timer_info.EndlessTimerInfo
import be.ugent.sel.studeez.screens.timer_form.form_screens.EndlessTimerFormScreen
import org.junit.Rule
import org.junit.Test

class TimerScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun timerFormScreenTest() {
        var save = false

        composeTestRule.setContent {
            EndlessTimerFormScreen(EndlessTimerInfo("", ""))
                .invoke(onSaveClick = {save = true})
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                text = "save",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        assert(save)
    }
}