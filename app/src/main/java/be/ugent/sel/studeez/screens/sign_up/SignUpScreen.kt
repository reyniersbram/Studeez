package be.ugent.sel.studeez.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.BasicTextButton
import be.ugent.sel.studeez.common.composable.EmailField
import be.ugent.sel.studeez.common.composable.PasswordField
import be.ugent.sel.studeez.common.composable.RepeatPasswordField
import be.ugent.sel.studeez.common.composable.SimpleScreenTemplate
import be.ugent.sel.studeez.common.composable.UsernameField
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.common.ext.textButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

data class SignUpActions(
    val onUserNameChange: (String) -> Unit,
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onRepeatPasswordChange: (String) -> Unit,
    val onSignUpClick: () -> Unit,
    val onLoginClick: () -> Unit,
)

fun getSignUpActions(
    viewModel: SignUpViewModel,
    openAndPopUp: (String, String) -> Unit,
): SignUpActions {
    return SignUpActions(
        onUserNameChange = { viewModel.onUsernameChange(it) },
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onRepeatPasswordChange = { viewModel.onRepeatPasswordChange(it) },
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
        onLoginClick = { viewModel.onLoginClick(openAndPopUp) },
    )
}

@Composable
fun SignUpRoute(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel,
) {
    val uiState by viewModel.uiState
    SignUpScreen(
        modifier = modifier,
        uiState,
        getSignUpActions(viewModel, openAndPopUp)
    )
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    signUpActions: SignUpActions,
) {
    val fieldModifier = Modifier.fieldModifier()
    SimpleScreenTemplate(title = resources().getString(AppText.create_account)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UsernameField(
                uiState.username,
                signUpActions.onUserNameChange,
                fieldModifier
            )
            EmailField(
                uiState.email,
                signUpActions.onEmailChange,
                fieldModifier
            )
            PasswordField(
                uiState.password,
                signUpActions.onPasswordChange,
                fieldModifier
            )
            RepeatPasswordField(
                uiState.repeatPassword,
                signUpActions.onRepeatPasswordChange,
                fieldModifier
            )
            BasicButton(
                AppText.create_account,
                Modifier.basicButton(),
                onClick = signUpActions.onSignUpClick
            )
            BasicTextButton(
                AppText.already_user,
                Modifier.textButton(),
                action = signUpActions.onLoginClick
            )
        }
    }
}

@Preview
@Composable
fun SignUpPreview() {
    SignUpScreen(
        uiState = SignUpUiState(),
        signUpActions = SignUpActions({}, {}, {}, {}, {}, {})
    )
}