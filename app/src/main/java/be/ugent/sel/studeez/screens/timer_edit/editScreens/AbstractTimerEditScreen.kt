package be.ugent.sel.studeez.screens.timer_edit.editScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.LabelledInputField
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo

abstract class AbstractTimerEditScreen(private val timerInfo: TimerInfo) {

    @Composable
    operator fun invoke(onSaveClick: (TimerInfo) -> Unit) {
        // TODO klassen hierarchie voor uistate
        // TODO klassen extras implementeren

        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Fields that every timer shares (ommited id)
            LabelledInputField(
                value = timerInfo.name,
                onNewValue = {},
                label = R.string.name
            )
            LabelledInputField(
                value = timerInfo.description,
                onNewValue = {},
                label = R.string.description,
                singleLine = false
            )

            ExtraFields()

            BasicButton(R.string.save, Modifier.basicButton()) {
                onSaveClick(timerInfo)
            }
        }
    }

    @Composable
    open fun ExtraFields() {
        // By default no extra fields, unless overwritten by subclass.
    }

}