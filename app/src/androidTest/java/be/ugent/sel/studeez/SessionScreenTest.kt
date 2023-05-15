package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalCustomTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalEndlessTimer
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalPomodoroTimer
import be.ugent.sel.studeez.screens.session.SessionActions
import be.ugent.sel.studeez.screens.session.sessionScreens.BreakSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.CustomSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.EndlessSessionScreen
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class SessionScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun customSessionScreenTest() {
        var endSession = false

        composeTestRule.setContent {
            CustomSessionScreen(
                functionalTimer = FunctionalCustomTimer(0),
                mediaplayer = null
            ).invoke(
                open = {},
                sessionActions = SessionActions(
                    {FunctionalCustomTimer(0)},
                    { "" },
                    {}, {}, {endSession = true}
                )
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                "end session",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()


        Assert.assertTrue(endSession)
    }

    @Test
    fun endlessSessionScreenTest() {
        var endSession = false

        composeTestRule.setContent {
            EndlessSessionScreen()
                .invoke(
                    open = {},
                    sessionActions = SessionActions(
                        {FunctionalEndlessTimer()},
                        { "" },
                        {}, {}, {endSession = true}
                    )
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                "end session",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()


        Assert.assertTrue(endSession)
    }

    @Test
    fun breakSessionScreenTest() {
        var endSession = false

        composeTestRule.setContent {
            BreakSessionScreen(
                funPomoDoroTimer = FunctionalPomodoroTimer(0, 0, 0),
                mediaplayer = null
            )
            .invoke(
                open = {},
                sessionActions = SessionActions(
                    {FunctionalPomodoroTimer(0, 0, 0)},
                    { "" },
                    {}, {}, {endSession = true}
                )
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                "end session",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()


        Assert.assertTrue(endSession)
    }
}
