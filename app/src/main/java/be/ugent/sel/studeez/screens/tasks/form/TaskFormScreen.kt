package be.ugent.sel.studeez.screens.tasks.form

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.DeleteButton
import be.ugent.sel.studeez.common.composable.FormComposable
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun TaskCreateRoute(
    goBack: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: TaskCreateFormViewModel,
) {
    val uiState by viewModel.uiState
    TaskForm(
        title = AppText.new_task,
        goBack = goBack,
        uiState = uiState,
        onConfirm = { viewModel.onCreate(openAndPopUp) },
        onNameChange = viewModel::onNameChange
    )
}

@Composable
fun TaskEditRoute(
    goBack: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: TaskEditFormViewModel,
) {
    val uiState by viewModel.uiState
    TaskForm(
        title = AppText.edit_task,
        goBack = goBack,
        uiState = uiState,
        onConfirm = { viewModel.onEdit(openAndPopUp) },
        onNameChange = viewModel::onNameChange
    ) {
        DeleteButton(text = AppText.delete_task) {
            viewModel.onDelete(openAndPopUp)
        }
    }
}

@Composable
fun TaskForm(
    @StringRes title: Int,
    goBack: () -> Unit,
    uiState: TaskFormUiState,
    onConfirm: () -> Unit,
    onNameChange: (String) -> Unit,
    extraButton: @Composable () -> Unit = {}
) {
    FormComposable(
        title = resources().getString(title),
        popUp = goBack,
    ) {
        Column {
            OutlinedTextField(
                singleLine = true,
                value = uiState.name,
                onValueChange = onNameChange,
                placeholder = { Text(stringResource(id = AppText.name)) },
                modifier = Modifier.fieldModifier(),
            )
            BasicButton(
                text = AppText.confirm,
                modifier = Modifier.basicButton(),
                onClick = onConfirm,
            )
            extraButton()
        }
    }
}

@Preview
@Composable
fun AddTaskFormPreview() {
    TaskForm(
        title = AppText.new_task,
        goBack = {},
        uiState = TaskFormUiState(),
        onConfirm = {},
        onNameChange = {},
    )
}

@Preview
@Composable
fun EditTaskFormPreview() {
    TaskForm(
        title = AppText.edit_task,
        goBack = {},
        uiState = TaskFormUiState(
            name = "Test Task",
        ),
        onConfirm = {},
        onNameChange = {},
    ) {
        DeleteButton(text = AppText.delete_task) {}
    }
}