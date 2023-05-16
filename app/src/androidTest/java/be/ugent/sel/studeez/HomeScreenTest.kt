package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.feed.FeedUiState
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.screens.home.HomeScreen
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreenTest() {
        var continueTask = false

        composeTestRule.setContent {
            HomeScreen(
                drawerActions = DrawerActions({}, {}, {}, {}, {}),
                navigationBarActions = NavigationBarActions({false}, {}, {}, {}, {}, {}, {}, {}),
                feedUiState = FeedUiState.Succes(mapOf(
                    "08 May 2023" to listOf(
                        FeedEntry(
                            argb_color = 0xFFABD200,
                            subJectName = "Test Subject",
                            taskName = "Test Task",
                            totalStudyTime = 600,
                        )
                    )
                )),
                continueTask = {_, _ -> continueTask = true },
                onEmptyFeedHelp = {},
                onViewFriendsClick = {},
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                "continue",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        Assert.assertTrue(continueTask)
    }

    @Test
    fun drawerTest() {
        var homebuttontest = false
        var timersbuttontest = false
        var settingsbuttontest = false
        var logoutbuttontest = false
        var aboutbuttontest = false

        composeTestRule.setContent {
            HomeScreen(
                drawerActions = DrawerActions(
                    {homebuttontest = true},
                    {timersbuttontest = true},
                    {settingsbuttontest = true},
                    {logoutbuttontest = true},
                    {aboutbuttontest = true}
                ),
                navigationBarActions = NavigationBarActions({false}, {}, {}, {}, {}, {}, {}, {}),
                feedUiState = FeedUiState.Succes(mapOf()),
                continueTask = {_, _ -> },
                onEmptyFeedHelp = {},
                onViewFriendsClick = {},
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onAllNodesWithText(
                "home",
                substring = true,
                ignoreCase = true
            )[2] // Third node has the button
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                "timer",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                "settings",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                "log out",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                "about",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        Assert.assertTrue(homebuttontest)
        Assert.assertTrue(timersbuttontest)
        Assert.assertTrue(settingsbuttontest)
        Assert.assertTrue(logoutbuttontest)
        Assert.assertTrue(aboutbuttontest)
    }

    @Test
    fun navigationbarTest() {
        var hometest = false
        var tasktest = false
        var friendstest = false
        var profiletest = false

        composeTestRule.setContent {
            HomeScreen(
                drawerActions = DrawerActions({}, {}, {}, {}, {}),
                navigationBarActions = NavigationBarActions(
                    {false},
                    {hometest = true},
                    {tasktest = true},
                    {friendstest = true},
                    {profiletest = true},
                    {}, {}, {}
                ),
                feedUiState = FeedUiState.Succes(mapOf()),
                continueTask = {_, _ -> },
                onEmptyFeedHelp = {},
                onViewFriendsClick = {},
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onAllNodesWithContentDescription(
                "Home",
                substring = true,
                ignoreCase = true
            )[0] // Third node has the button
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(
                "tasks",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(
                "feed",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithContentDescription(
                "profile",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        Assert.assertTrue(hometest)
        Assert.assertTrue(tasktest)
        Assert.assertTrue(friendstest)
        Assert.assertTrue(profiletest)
    }
}