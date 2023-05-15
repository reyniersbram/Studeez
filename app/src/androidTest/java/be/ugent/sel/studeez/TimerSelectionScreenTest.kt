package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.screens.timer_selection.TimerSelectionActions
import be.ugent.sel.studeez.screens.timer_selection.TimerSelectionScreen
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class TimerSelectionScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun timerOverviewScreenTest() {
        var start = false

        composeTestRule.setContent {
            TimerSelectionScreen(
                timerSelectionActions = TimerSelectionActions({ flowOf()}, {start = true}, 0),
                popUp = {}
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                text = "start",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        assert(start)
    }
}