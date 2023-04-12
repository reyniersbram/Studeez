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
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.common.composable.*
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.common.ext.textButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    // TODO Make this a separate kind of screen?
    SecondaryScreenToolbar(title = resources().getString(AppText.sign_in), {}) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
            PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

            BasicButton(AppText.sign_in, Modifier.basicButton()) { viewModel.onSignInClick(openAndPopUp) }

            BasicTextButton(AppText.not_already_user, Modifier.textButton()) {
                viewModel.onNotAlreadyUser(openAndPopUp)
            }

            BasicTextButton(AppText.forgot_password, Modifier.textButton()) {
                viewModel.onForgotPasswordClick()
            }
        }
    }
}