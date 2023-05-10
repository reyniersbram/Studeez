package be.ugent.sel.studeez.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.card
import be.ugent.sel.studeez.common.ext.defaultButtonShape
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun BasicTextButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
    TextButton(
        onClick = action,
        modifier = modifier
    ) {
        Text(
            text = stringResource(text)
        )
    }
}

@Composable
fun BasicButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    border: BorderStroke? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = defaultButtonShape(),
        colors = colors,
        border = border,
        enabled = enabled,
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
    BasicButton(text = AppText.add_timer, modifier = Modifier.basicButton()) {}
}

@Composable
fun StealthButton(
    @StringRes text: Int,
    modifier: Modifier = Modifier.card(),
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    //val clickablemodifier = if (disabled) Modifier.clickable(indication = null) else modifier
    val borderColor = if (enabled) MaterialTheme.colors.primary
                      else MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
    BasicButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = borderColor
        ),
        border = BorderStroke(2.dp, borderColor)
    )
}

@Preview
@Composable
fun StealthButtonCardPreview() {
    StealthButton(text = AppText.edit) {

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
            backgroundColor = MaterialTheme.colors.error,
            contentColor = MaterialTheme.colors.onSurface,
        ),
    )
}

@Preview
@Composable
fun DeleteButtonPreview() {
    DeleteButton(text = AppText.delete_subject) {}
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
            contentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
        ),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.4f)),
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
    NewTaskSubjectButton(onClick = {}, text = AppText.new_task)
}