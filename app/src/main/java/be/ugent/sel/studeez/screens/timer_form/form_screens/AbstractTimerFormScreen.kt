package be.ugent.sel.studeez.screens.timer_form.form_screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.LabelledInputField
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.R.string as AppText

abstract class AbstractTimerFormScreen(private val timerInfo: TimerInfo) {

    @Composable
    operator fun invoke(onSaveClick: (TimerInfo) -> Unit) {

        var name by remember { mutableStateOf(timerInfo.name) }
        var description by remember { mutableStateOf(timerInfo.description) }

        // This shall rerun whenever name and description change
        timerInfo.name = name
        timerInfo.description = description

        Column {

            // Fields that every timer shares (ommited id)
            LabelledInputField(
                value = name,
                onNewValue = { name = it },
                label = R.string.name
            )

            LabelledInputField(
                value = description,
                onNewValue = { description = it },
                label = AppText.description,
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