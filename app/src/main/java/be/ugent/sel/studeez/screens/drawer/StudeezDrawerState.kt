package be.ugent.sel.studeez.screens.drawer

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class StudeezDrawerState(
    val scaffoldState: ScaffoldState,
    private val coroutineScope: CoroutineScope
) {
    fun openDrawer() {
        coroutineScope.launch { scaffoldState.drawerState.open() }
    }
}
