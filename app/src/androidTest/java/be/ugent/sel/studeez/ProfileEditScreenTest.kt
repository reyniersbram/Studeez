package be.ugent.sel.studeez

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.screens.profile.edit_profile.EditProfileActions
import be.ugent.sel.studeez.screens.profile.edit_profile.EditProfileScreen
import be.ugent.sel.studeez.screens.profile.edit_profile.ProfileEditUiState
import org.junit.Rule
import org.junit.Test

class ProfileEditScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileEditScreenTest() {
        var edit_save = false
        var goback = false
        var delete_click = false

        composeTestRule.setContent {
            EditProfileScreen(
                goBack = {goback = true},
                uiState = ProfileEditUiState(),
                editProfileActions = EditProfileActions(
                    onUserNameChange = {},
                    onBiographyChange = {},
                    onSaveClick = {edit_save = true},
                    onDeleteClick = { delete_click = true },
                ),
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

        assert(edit_save)
        assert(goback)
        assert(delete_click)
    }
}