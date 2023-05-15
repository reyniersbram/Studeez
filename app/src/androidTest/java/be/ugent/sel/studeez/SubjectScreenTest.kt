package be.ugent.sel.studeez

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.common.composable.DeleteButton
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.screens.subjects.SubjectScreen
import be.ugent.sel.studeez.screens.subjects.SubjectUiState
import be.ugent.sel.studeez.screens.subjects.form.SubjectForm
import be.ugent.sel.studeez.screens.subjects.form.SubjectFormUiState
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class SubjectScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addSubjectScreenTest() {
        var confirm = false
        var goback = false

        composeTestRule.setContent {
            SubjectForm(
                title = R.string.new_subject,
                goBack = {goback = true},
                uiState = SubjectFormUiState(),
                onConfirm = {confirm = true},
                onNameChange = {},
                onColorChange = {},
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
    fun editSubjectScreenTest() {
        var confirm = false
        var delete = false

        composeTestRule.setContent {
            SubjectForm(
                title = R.string.edit_subject,
                goBack = {},
                uiState = SubjectFormUiState(
                    name = "Test Subject",
                ),
                onConfirm = {confirm = true},
                onNameChange = {},
                onColorChange = {},
            )
            DeleteButton(text = R.string.delete_subject) {
                delete = true
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
    fun subjectScreenTest() {
        var view = false
        var add = false

        composeTestRule.setContent {
            SubjectScreen(
                drawerActions = DrawerActions({}, {}, {}, {}, {}),
                navigationBarActions = NavigationBarActions({false}, {}, {}, {}, {}, {}, {}, {}),
                onAddSubject = { add = true },
                onViewSubject = { view = true },
                getStudyTime = { flowOf() },
                getCompletedTaskCount = { flowOf() },
                getTaskCount = { flowOf() },
                uiState = SubjectUiState.Succes(
                    listOf(
                        Subject(
                            id = "",
                            name = "Test Subject",
                            argb_color = 0xFFFFD200,
                            archived = false
                        )
                    )
                )
            )
        }

        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(
                text = "view",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                text = "new subject",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        assert(add)
        assert(view)
    }
}