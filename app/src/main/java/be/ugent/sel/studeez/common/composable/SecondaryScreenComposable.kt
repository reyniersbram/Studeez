package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme

@Composable
// Does not contain floatingActionButton and bottom bar, used in all the other screens
fun SecondaryScreenTemplate(
    title: String,
    popUp: () -> Unit,
    barAction: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        // Everything at the top of the screen
        topBar = { TopAppBar(
            title = { Text(text = title) },
            navigationIcon = {
                IconButton(onClick = { popUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = resources().getString(R.string.go_back)
                    )
                }
            },
            actions = barAction
        ) },
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Preview
@Composable
fun SecondaryScreenToolbarPreview() {
    StudeezTheme { SecondaryScreenTemplate(
        "Preview screen",
        {}
    ) {} }
}