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
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.common.composable.*
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.common.ext.fieldModifier
import be.ugent.sel.studeez.common.ext.textButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun SignUpScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()

    SecondaryScreenTemplate(title = resources().getString(AppText.create_account), {}) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)
            PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)
            RepeatPasswordField(uiState.repeatPassword, viewModel::onRepeatPasswordChange, fieldModifier)

            BasicButton(AppText.create_account, Modifier.basicButton()) {
                viewModel.onSignUpClick(openAndPopUp)
            }

            BasicTextButton(AppText.already_user, Modifier.textButton()) {
                viewModel.onLoginScreenClick(openAndPopUp)
            }
        }
    }


}