package be.ugent.sel.studeez

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.screens.splash.SplashScreen
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreenTest() {
        var tryAgain = false

        composeTestRule.setContent {
            SplashScreen(
                Modifier,
                {tryAgain = true},
                true
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onAllNodesWithText(
                text = "try again",
                substring = true,
                ignoreCase = true
            )[1] // Second node is the button
            .assertExists()
            .performClick()

        assert(tryAgain)
    }
}