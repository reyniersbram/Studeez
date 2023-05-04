package be.ugent.sel.studeez.common.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.resources
import kotlin.math.sin
import be.ugent.sel.studeez.R.drawable as AppIcon
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun BasicField(
    @StringRes text: Int,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(text)) }
    )
}

@Composable
fun LabelledInputField(
    value: String,
    onNewValue: (String) -> Unit,
    @StringRes label: Int,
    singleLine: Boolean = false
) {
    OutlinedTextField(
        value = value,
        singleLine = singleLine,
        onValueChange = onNewValue,
        label = { Text(text = stringResource(id = label)) },
        modifier = Modifier.fieldModifier()
    )
}

@Composable
fun UsernameField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(AppText.username)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Username") }
    )
}

@Composable
fun EmailField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(AppText.email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "Email") }
    )
}

@Composable
fun LabeledNumberInputField(
    value: Int,
    onNewValue: (Int) -> Unit,
    @StringRes label: Int,
    singleLine: Boolean = false
) {
    var number by remember { mutableStateOf(value) }
    OutlinedTextField(
        value = number.toString(),
        singleLine = singleLine,
        label = { Text(resources().getString(label)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {typedInt ->
            val isNumber = typedInt.matches(Regex("[1-9]+\\d*]"))
            if (isNumber) {
                number = typedInt.toInt()
                onNewValue(typedInt.toInt())
            }
        }
    )
}

@Composable
fun LabeledErrorTextField(
    modifier: Modifier = Modifier,
    initialValue: String,
    @StringRes label: Int,
    singleLine: Boolean = false,
    errorText: String,
    keyboardType: KeyboardType,
    predicate: (String) -> Boolean,
    onNewCorrectValue: (String) -> Unit
) {
    var value by remember {
        mutableStateOf(initialValue)
    }

    var isValid by remember {
        mutableStateOf(predicate(value))
    }

    Column {
        OutlinedTextField(
            modifier = modifier.fieldModifier(),
            value = value,
            onValueChange = { newText ->
                value = newText
                isValid = predicate(value)
                if (isValid) {
                    onNewCorrectValue(newText)
                }
            },
            singleLine = singleLine,
            label = { Text(text = resources().getString(label)) },
            isError = !isValid,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            )
        )

        if (!isValid) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = errorText,
                color = MaterialTheme.colors.error
            )
        }
    }
}

fun isNumber(string: String): Boolean {
    return string.matches(Regex("[1-9]+\\d*"))
}



 @Preview(showBackground = true)
 @Composable
 fun IntInputPreview() {
     LabeledNumberInputField(value = 1, onNewValue = {}, label = AppText.email)
 }

@Composable
fun PasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, AppText.password, onNewValue, modifier)
}

@Composable
fun RepeatPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, AppText.repeat_password, onNewValue, modifier)
}

@Composable
private fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(AppIcon.ic_visibility_on)
        else painterResource(AppIcon.ic_visibility_off)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}