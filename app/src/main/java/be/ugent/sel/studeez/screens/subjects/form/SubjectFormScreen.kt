package be.ugent.sel.studeez.screens.subjects.form

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.DeleteButton
import be.ugent.sel.studeez.common.composable.FormComposable
import be.ugent.sel.studeez.common.composable.LabelledInputField
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.common.ext.generateRandomArgb
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.launch
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun SubjectCreateRoute(
    goBack: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SubjectCreateFormViewModel,
) {
    val uiState by viewModel.uiState
    SubjectForm(
        title = AppText.new_subject,
        goBack = goBack,
        uiState = uiState,
        onConfirm = { viewModel.onCreate(openAndPopUp) },
        onNameChange = viewModel::onNameChange,
        onColorChange = viewModel::onColorChange,
    )
}

@Composable
fun SubjectEditRoute(
    goBack: () -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SubjectEditFormViewModel,
) {
    val uiState by viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    SubjectForm(
        title = AppText.edit_subject,
        goBack = goBack,
        uiState = uiState,
        onConfirm = { viewModel.onEdit(openAndPopUp) },
        onNameChange = viewModel::onNameChange,
        onColorChange = viewModel::onColorChange,
    ) {
        DeleteButton(text = AppText.delete_subject) {
            coroutineScope.launch {
                viewModel.onDelete(openAndPopUp)
            }
        }
    }
}

@Composable
fun SubjectForm(
    @StringRes title: Int,
    goBack: () -> Unit,
    uiState: SubjectFormUiState,
    onConfirm: () -> Unit,
    onNameChange: (String) -> Unit,
    onColorChange: (Long) -> Unit,
    extraButton: @Composable () -> Unit = {},
) {
    FormComposable(
        title = resources().getString(title),
        popUp = goBack,
    ) {
        Column {
            LabelledInputField(
                singleLine = true,
                value = uiState.name,
                onNewValue = onNameChange,
                label = AppText.name,
            )
            ColorPicker(onColorChange, uiState)
            BasicButton(
                text = AppText.confirm,
                modifier = Modifier.basicButton(),
                onClick = onConfirm,
            )
            extraButton()
        }
    }
}

@Composable
fun ColorPicker(
    onColorChange: (Long) -> Unit,
    uiState: SubjectFormUiState,
) {
    Button(
        onClick = { onColorChange(Color.generateRandomArgb()) },
        modifier = Modifier.fieldModifier(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(uiState.color),
            contentColor = Color.White,
        ),
        shape = RoundedCornerShape(4.dp),
    ) {
        Text(text = stringResource(id = AppText.regenerate_color))
    }
}

@Preview
@Composable
fun AddSubjectFormPreview() {
    SubjectForm(
        title = AppText.new_subject,
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
        title = AppText.edit_subject,
        goBack = {},
        uiState = SubjectFormUiState(
            name = "Test Subject",
        ),
        onConfirm = {},
        onNameChange = {},
        onColorChange = {},
    ) {
        DeleteButton(text = AppText.delete_subject) {}
    }
}