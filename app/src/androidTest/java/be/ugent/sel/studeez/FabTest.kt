package be.ugent.sel.studeez

import androidx.compose.material.FloatingActionButton
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.common.composable.AddButtonActions
import be.ugent.sel.studeez.common.composable.ExpandedAddButton
import org.junit.Rule
import org.junit.Test

class FabTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun expandFabTest() {
        var expand = false

        composeTestRule.setContent {
            FloatingActionButton(
                onClick = {expand = true}
            ) {}
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNode(hasClickAction())
            .assertExists()
            .performClick()

        assert(expand)
    }

    @Test
    fun fabTest() {
        var task = false
        var session = false
        var friend = false

        composeTestRule.setContent {
            ExpandedAddButton(
                addButtonActions = AddButtonActions(
                    {task = true},
                    {friend = true},
                    {session = true}
                )
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription("Session")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Task")
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Friend")
            .assertExists()
            .performClick()

        assert(task)
        assert(session)
        assert(friend)
    }
}