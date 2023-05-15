package be.ugent.sel.studeez

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.screens.session_recap.SessionRecapActions
import be.ugent.sel.studeez.screens.session_recap.SessionRecapScreen
import com.google.firebase.Timestamp
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class SessionScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun SessionRecapTest() {
        var saveCalled = false
        var discardCalled = false

        composeTestRule.setContent {
            SessionRecapScreen(
                Modifier,
                SessionRecapActions(
                    {
                        SessionReport(
                            "",
                            0,
                            Timestamp(0, 0),
                            "")
                    },
                    { saveCalled = true },
                    { discardCalled = true }
                )
            )
        }
        composeTestRule
            .onNodeWithText(
                "You studied",
                substring = true,
                ignoreCase = true
            )
            .assertExists()

        composeTestRule
            .onNodeWithText(
                "save",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        composeTestRule
            .onNodeWithText(
                "discard",
                substring = true,
                ignoreCase = true
            )
            .assertExists()
            .performClick()

        Assert.assertTrue(saveCalled)
        Assert.assertTrue(discardCalled)
    }
}
