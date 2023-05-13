package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FormComposable(
    title: String,
    popUp: () -> Unit,
    content: @Composable () -> Unit,
) {
    SecondaryScreenTemplate(title = title, popUp = popUp) {
        Box(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            content()
        }
    }
}