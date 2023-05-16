package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.screens.sign_up.SignUpActions
import be.ugent.sel.studeez.screens.sign_up.SignUpScreen
import be.ugent.sel.studeez.screens.sign_up.SignUpUiState
import org.junit.Rule
import org.junit.Test

class SignUpScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun signupScreenTest() {
        var create = false
        var login = false

        composeTestRule.setContent {
            SignUpScreen(
                uiState = SignUpUiState(),
                signUpActions = SignUpActions({}, {}, {}, {}, {create = true}, {login = true})
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                text = "log in",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onAllNodesWithText(
                text = "Create account",
                substring = true,
                ignoreCase = true
            )[0] // First node has the button
            .assertExists()
            .performClick()

        assert(login)
        assert(create)
    }
}