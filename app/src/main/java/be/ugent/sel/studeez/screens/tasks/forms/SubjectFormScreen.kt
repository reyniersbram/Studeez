package be.ugent.sel.studeez.screens.tasks.forms

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.DeleteButton
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.resources

@Composable
fun SubjectAddRoute(
    goBack: () -> Unit,
    open: (String) -> Unit,
    viewModel: SubjectFormViewModel,
) {
    val uiState by viewModel.uiState
    SubjectForm(
        title = R.string.new_subject,
        goBack = goBack,
        uiState = uiState,
        onConfirm = { viewModel.onCreate(open) },
        onNameChange = viewModel::onNameChange,
        onColorChange = {},
    )
}

@Composable
fun SubjectEditRoute(
    goBack: () -> Unit,
    open: (String) -> Unit,
    viewModel: SubjectFormViewModel,
) {
    val uiState by viewModel.uiState
    SubjectForm(
        title = R.string.edit_subject,
        goBack = goBack,
        uiState = uiState,
        onConfirm = { viewModel.onEdit(open) },
        onNameChange = viewModel::onNameChange,
        onColorChange = {},
    ) {
        DeleteButton(onClick = { viewModel.onDelete(open) })
    }
}

@Composable
fun SubjectForm(
    @StringRes title: Int,
    goBack: () -> Unit,
    uiState: SubjectFormUiState,
    onConfirm: () -> Unit,
    onNameChange: (String) -> Unit,
    onColorChange: (Color) -> Unit,
    extraButton: @Composable () -> Unit = {},
) {
    SecondaryScreenTemplate(
        title = resources().getString(title),
        popUp = goBack,
    ) {
        Column {
            OutlinedTextField(
                singleLine = true,
                value = uiState.name,
                onValueChange = onNameChange,
                placeholder = { Text(stringResource(id = R.string.username)) },
                modifier = Modifier.fieldModifier(),
            )
            BasicButton(
                text = R.string.confirm,
                modifier = Modifier.basicButton(),
                onClick = onConfirm,
            )
            extraButton()
        }
    }
}

@Preview
@Composable
fun AddSubjectFormPreview() {
    SubjectForm(
        title = R.string.new_subject,
        goBack = {},
        uiState = SubjectFormUiState(),
        onConfirm = {},
        onNameChange = {},
        onColorChange = {},
    )
}

@Preview
@Composable
fun EditSubjectFormPreview() {
    SubjectForm(
        title = R.string.edit_subject,
        goBack = {},
        uiState = SubjectFormUiState(
            name = "Test Subject",
        ),
        onConfirm = {},
        onNameChange = {},
        onColorChange = {},
    ) {
        DeleteButton {}
    }
}