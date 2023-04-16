package be.ugent.sel.studeez.screens.profile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme

@Composable
fun EditProfileScreen(
    goBack: () -> Unit
) {
    SecondaryScreenTemplate(
        title = resources().getString(R.string.editing_profile),
        popUp = goBack
    ) {
        Text(text = "TODO")
    }
}

@Preview
@Composable
fun EditProfileScreenComposable() {
    StudeezTheme {
        EditProfileScreen {
            {}
        }
    }
}