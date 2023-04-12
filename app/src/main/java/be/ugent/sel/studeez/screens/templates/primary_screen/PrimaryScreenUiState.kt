package be.ugent.sel.studeez.screens.templates.primary_screen

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import be.ugent.sel.studeez.StudeezAppstate

@Composable
fun rememberPrimaryScreenState(
    scaffoldState: ScaffoldState = rememberScaffoldState()
) = remember(scaffoldState) {
    StudeezAppstate(scaffoldState)
}