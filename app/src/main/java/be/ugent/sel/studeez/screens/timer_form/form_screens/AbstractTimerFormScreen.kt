package be.ugent.sel.studeez.screens.timer_form.form_screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.LabeledErrorTextField
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import be.ugent.sel.studeez.R.string as AppText

abstract class AbstractTimerFormScreen(private val timerInfo: TimerInfo) {

    protected val valids = mutableMapOf(
        "name" to mutableStateOf(textPredicate(timerInfo.name)),
        "description" to mutableStateOf(textPredicate(timerInfo.description))
    )

    protected val firsts = mutableMapOf(
        "name" to mutableStateOf(true),
        "description" to mutableStateOf(true)
    )


    @Composable
    operator fun invoke(
        onSaveClick: (TimerInfo) -> Unit,
        extraButton: @Composable () -> Unit
    ) {

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
                    isValid = valids.getValue("name"),
                    isFirst = firsts.getValue("name"),
                    keyboardType = KeyboardType.Text,
                    predicate = { it.isNotBlank() }
                ) { correctName ->
                    timerInfo.name = correctName
                }

                LabeledErrorTextField(
                    initialValue = timerInfo.description,
                    label = R.string.description,
                    errorText = AppText.description_error,
                    isValid = valids.getValue("description"),
                    isFirst = firsts.getValue("description"),
                    singleLine= false,
                    keyboardType = KeyboardType.Text,
                    predicate = { textPredicate(it) }
                ) { correctName ->
                    timerInfo.description = correctName
                }

                ExtraFields()

            }

            Column {
                BasicButton(R.string.save, Modifier.basicButton()) {
                    if (valids.all { it.component2().value }) { // All fields are valid
                        onSaveClick(timerInfo)
                    } else {
                        firsts.map { it.component2().value = false } // dont mask error because its not been filled out yet
                        SnackbarManager.showMessage(AppText.fill_out_error)
                    }
                }
                extraButton()
            }
        }
    }

    private fun textPredicate(text: String): Boolean {
        return text.isNotBlank()
    }

    @Composable
    open fun ExtraFields() {
        // By default no extra fields, unless overwritten by subclass.
    }

}