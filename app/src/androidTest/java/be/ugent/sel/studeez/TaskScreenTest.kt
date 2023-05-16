package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.common.composable.DeleteButton
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.screens.tasks.TaskActions
import be.ugent.sel.studeez.screens.tasks.TaskScreen
import be.ugent.sel.studeez.screens.tasks.form.TaskForm
import be.ugent.sel.studeez.screens.tasks.form.TaskFormUiState
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class TaskScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addTaskScreenTest() {
        var confirm = false
        var goback = false

        composeTestRule.setContent {
            TaskForm(
                title = R.string.new_task,
                goBack = {goback = true},
                uiState = TaskFormUiState(),
                onConfirm = {confirm = true},
                onNameChange = {},
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                text = "confirm",
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

        assert(confirm)
        assert(goback)
    }

    @Test
    fun editTaskScreenTest() {
        var confirm = false
        var delete = false

        composeTestRule.setContent {
            TaskForm(
                title = R.string.edit_task,
                goBack = {},
                uiState = TaskFormUiState(
                    name = "Test Task",
                ),
                onConfirm = {confirm = true},
                onNameChange = {},
            ) {
                DeleteButton(text = R.string.delete_task) {
                    delete = true
                }
            }
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                text = "confirm",
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

        assert(confirm)
        assert(delete)
    }

    @Test
    fun taskScreenTest() {
        var add = false
        var edit = false
        var start = false

        composeTestRule.setContent {
            TaskScreen(
                goBack = {},
                taskActions = TaskActions(
                    {add = true},
                    { Subject(name = "Test Subject") },
                    { flowOf(listOf(Task())) },
                    { _, _ -> run {} },
                    {edit = true},
                    {start = true},
                    {},
                )
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription(
                label = "edit",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                text = "new",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                text = "start",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        assert(add)
        assert(edit)
        assert(start)
    }
}