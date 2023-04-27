package be.ugent.sel.studeez.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
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
    modifier: Modifier,
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
    onClick: () -> Unit,
) {
    BasicButton(
        text = text,
        onClick = onClick,
        modifier = Modifier.card(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.DarkGray,
        ),
        border = BorderStroke(3.dp, Color.DarkGray),
    )
}

@Preview
@Composable
fun StealthButtonCardPreview() {
    StealthButton(text = R.string.edit) {

    }
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