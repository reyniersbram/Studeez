package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.screens.log_in.LoginScreen
import be.ugent.sel.studeez.screens.log_in.LoginScreenActions
import be.ugent.sel.studeez.screens.log_in.LoginUiState
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreenTest() {
        var login = false
        var signup = false
        var forgot_password = false

        composeTestRule.setContent {
            LoginScreen(
                uiState = LoginUiState(),
                loginScreenActions = LoginScreenActions(
                    {}, {},
                    {signup = true},
                    {login = true},
                    {forgot_password = true}
                )
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onAllNodesWithText(
                text = "Sign in",
                substring = true,
                ignoreCase = true
            )[0] // The first object is the button
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                text = "Forgot",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                text = "Sign up",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        assert(signup)
        assert(login)
        assert(forgot_password)
    }
}