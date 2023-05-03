package be.ugent.sel.studeez.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.card

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    TextButton(onClick = action, modifier = modifier) { Text(text = stringResource(text)) }
}

@Composable
fun BasicButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = colors,
        border = border,
    ) {
        Text(
            text = stringResource(text),
            fontSize = 16.sp
        )
    }
}

@Preview
@Composable
fun BasicButtonPreview() {
    BasicButton(text = R.string.add_timer, modifier = Modifier.basicButton()) {}
}

@Composable
fun StealthButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier.card(),
    onClick: () -> Unit,
) {
    BasicButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface)
    )
}

@Preview
@Composable
fun StealthButtonCardPreview() {
    StealthButton(text = R.string.edit) {

    }
}


@Composable
fun DeleteButton(
    @StringRes text: Int,
    onClick: () -> Unit,
) {
    BasicButton(
        text = text,
        modifier = Modifier.basicButton(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = Color.White,
        ),
    )
}

@Preview
@Composable
fun DeleteButtonPreview() {
    DeleteButton(text = R.string.delete_subject) {}
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action
    ) {
        Text(text = stringResource(text))
    }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
        ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.onPrimary,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Text(text = stringResource(text))
    }
}

@Composable
fun NewTaskSubjectButton(
    onClick: () -> Unit,
    @StringRes text: Int,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp, 5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.Gray,
        ),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = null,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = stringResource(id = text))
            Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = text))
        }
    }
}

@Preview
@Composable
fun NewTaskButtonPreview() {
    NewTaskSubjectButton(onClick = {}, text = R.string.new_task)
}