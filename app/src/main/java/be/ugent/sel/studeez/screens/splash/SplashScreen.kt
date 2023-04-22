package be.ugent.sel.studeez.screens.splash

import android.window.SplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.ext.basicButton
import kotlinx.coroutines.delay
import be.ugent.sel.studeez.R.string as AppText

private const val SPLASH_TIMEOUT = 500L

@Composable
fun SplashRoute(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel,
) {
    SplashScreen(
        modifier = modifier,
        onAppStart = { viewModel.onAppStart(openAndPopUp) },
        showError = viewModel.showError.value
    )
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onAppStart: () -> Unit,
    showError: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showError) {
            Text(text = stringResource(AppText.generic_error))
            BasicButton(
                AppText.try_again,
                Modifier.basicButton(),
                onClick = onAppStart,
            )
        } else {
            CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
        }
    }
    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        onAppStart()
    }
}

@Preview
@Composable
fun SplashPreview() {
    SplashScreen(
        onAppStart = {},
        showError = false,
    )
}

@Preview
@Composable
fun SplashErrorPreview() {
    SplashScreen(
        onAppStart = {},
        showError = true,
    )
}