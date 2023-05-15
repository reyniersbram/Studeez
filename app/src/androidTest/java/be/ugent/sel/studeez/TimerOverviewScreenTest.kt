package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.data.local.models.timer_info.EndlessTimerInfo
import be.ugent.sel.studeez.screens.timer_overview.TimerOverviewActions
import be.ugent.sel.studeez.screens.timer_overview.TimerOverviewScreen
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class TimerOverviewScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun timerOverviewScreenTest() {
        var add = false
        var edit = false

        composeTestRule.setContent {
            TimerOverviewScreen(
                timerOverviewActions = TimerOverviewActions(
                    { flowOf(listOf(EndlessTimerInfo("", ""))) },
                    { listOf() },
                    {edit = true},
                    {add = true}
                ),
                drawerActions = DrawerActions({}, {}, {}, {}, {})
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                text = "add",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                text = "edit",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        assert(add)
        assert(edit)
    }
}