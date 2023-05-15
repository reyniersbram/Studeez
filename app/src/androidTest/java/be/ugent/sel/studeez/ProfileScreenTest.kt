package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.screens.profile.ProfileActions
import be.ugent.sel.studeez.screens.profile.ProfileScreen
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileScreenTest() {
        var edit = false

        composeTestRule.setContent {
            ProfileScreen(
                profileActions = ProfileActions({null}, {edit = true}),
                drawerActions = DrawerActions({}, {}, {}, {}, {}),
                navigationBarActions = NavigationBarActions({ false }, {}, {}, {}, {}, {}, {}, {})
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription(
                label = "edit profile",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        assert(edit)
    }
}