package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.screens.profile.EditProfileActions
import be.ugent.sel.studeez.screens.profile.EditProfileScreen
import be.ugent.sel.studeez.screens.profile.ProfileEditUiState
import org.junit.Rule
import org.junit.Test

class ProfileEditScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileEditScreenTest() {
        var edit_save = false
        var edit_cancel = false
        var goback = false

        composeTestRule.setContent {
            EditProfileScreen(
                goBack = {goback = true},
                uiState = ProfileEditUiState(),
                editProfileActions = EditProfileActions(
                    {},
                    {edit_save = true},
                    {edit_cancel = true}
                )
            )
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

        composeTestRule
            .onNodeWithText(
                text = "delete",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(
                label = "go back",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        assert(edit_cancel)
        assert(edit_save)
        assert(goback)
    }
}