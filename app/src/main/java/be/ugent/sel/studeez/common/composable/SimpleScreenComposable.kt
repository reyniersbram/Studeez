package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun SimpleScreenTemplate(
    title: String,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold( topBar = { TopAppBar ( title = { Text(text = title) } ) }
    ) { paddingValues -> content(paddingValues) }
}