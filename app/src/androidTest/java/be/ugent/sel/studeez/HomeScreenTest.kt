package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.feed.FeedUiState
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.screens.home.HomeScreen
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun sessionRecapTestt() {
        var saveCalled = false

        composeTestRule.setContent {
            HomeScreen(
                open = {},
                drawerActions = DrawerActions({}, {}, {}, {}, {}),
                navigationBarActions = NavigationBarActions({false}, {}, {}, {}, {}, {}, {}, {}),
                feedUiState = FeedUiState.Succes(mapOf()),
                continueTask = {_, _ -> },
                onEmptyFeedHelp = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                "continue",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()
    }
}