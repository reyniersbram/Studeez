package be.ugent.sel.studeez.screens.log_in

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
import be.ugent.sel.studeez.common.composable.*
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.common.ext.textButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

data class LoginScreenActions(
    val onEmailChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onSignUpClick: () -> Unit,
    val onSignInClick: () -> Unit,
    val onForgotPasswordClick: () -> Unit,
)

fun getLoginScreenActions(
    viewModel: LoginViewModel,
    openAndPopUp: (String, String) -> Unit,
): LoginScreenActions {
    return LoginScreenActions(
        onEmailChange = { viewModel.onEmailChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
        onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
        onForgotPasswordClick = { viewModel.onForgotPasswordClick() }
    )
}

@Composable
fun LoginRoute(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
) {
    val uiState by viewModel.uiState

    LoginScreen(
        modifier = modifier,
        uiState = uiState,
        loginScreenActions = getLoginScreenActions(viewModel = viewModel, openAndPopUp)
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    loginScreenActions: LoginScreenActions,
) {
    SimpleScreenTemplate(title = resources().getString(AppText.sign_in)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailField(
                uiState.email,
                loginScreenActions.onEmailChange,
                Modifier.fieldModifier()
            )
            PasswordField(
                uiState.password,
                loginScreenActions.onPasswordChange,
                Modifier.fieldModifier()
            )
            BasicButton(
                AppText.sign_in,
                Modifier.basicButton(),
                onClick = loginScreenActions.onSignInClick,
            )

            BasicTextButton(
                AppText.not_already_user,
                Modifier.textButton(),
                action = loginScreenActions.onSignUpClick,
            )

            BasicTextButton(
                AppText.forgot_password,
                Modifier.textButton(),
                action = loginScreenActions.onForgotPasswordClick,
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        loginScreenActions = LoginScreenActions({}, {}, {}, {}, {})
    )
}