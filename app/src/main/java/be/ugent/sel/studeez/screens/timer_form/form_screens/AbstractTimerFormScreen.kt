package be.ugent.sel.studeez.screens.timer_form.form_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.LabeledErrorTextField
import be.ugent.sel.studeez.common.composable.LabelledInputField
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.R.string as AppText

abstract class AbstractTimerFormScreen(private val timerInfo: TimerInfo) {

    @Composable
    operator fun invoke(
        onSaveClick: (TimerInfo) -> Unit,
        extraButton: @Composable () -> Unit
    ) {


        var name by remember { mutableStateOf(timerInfo.name) }
        val isNameValid = remember { mutableStateOf(false) }
        val hasEditedName = remember { mutableStateOf(true) }

        var description by remember { mutableStateOf(timerInfo.description) }
        val isDescriptionValid = remember { mutableStateOf(false) }
        val hasEditedDescription = remember { mutableStateOf(true) }

        // This shall rerun whenever name and description change
        timerInfo.name = name
        timerInfo.description = description

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Fields that every timer shares (ommited id)
                LabeledErrorTextField(
                    initialValue = timerInfo.name,
                    label = R.string.name,
                    errorText = AppText.name_error,
                    isValid = isNameValid,
                    isFirst = hasEditedName,
                    keyboardType = KeyboardType.Text,
                    predicate = { it.isNotBlank() }
                ) { correctName ->
                    name = correctName
                }

                LabeledErrorTextField(
                    initialValue = timerInfo.description,
                    label = R.string.description,
                    errorText = AppText.description_error,
                    isValid = isDescriptionValid,
                    isFirst = hasEditedDescription,
                    singleLine= false,
                    keyboardType = KeyboardType.Text,
                    predicate = { it.isNotBlank() }
                ) { correctName ->
                    description = correctName
                }

                ExtraFields()

            }

            Column {
                BasicButton(R.string.save, Modifier.basicButton()) {
                    if (isNameValid.value && isDescriptionValid.value) {
                        onSaveClick(timerInfo)
                    } else {
                        hasEditedName.value = false
                        hasEditedDescription.value = false
                    }
                }
                extraButton()
            }
        }
    }

    @Composable
    open fun ExtraFields() {
        // By default no extra fields, unless overwritten by subclass.
    }

}