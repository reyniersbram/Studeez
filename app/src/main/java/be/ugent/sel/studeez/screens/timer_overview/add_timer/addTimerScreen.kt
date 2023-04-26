package be.ugent.sel.studeez.screens.timer_overview.add_timer

import androidx.compose.runtime.Composable
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.drawer.DrawerViewModel
import be.ugent.sel.studeez.common.composable.drawer.getDrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarViewModel
import be.ugent.sel.studeez.common.composable.navbar.getNavigationBarActions

@Composable
fun AddTimerRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit
    viewModel: AddTimerViewModel,
    drawerViewModel: DrawerViewModel,
    navBarViewModel: NavigationBarViewModel,
) {
    addTimerScreen(
        drawerActions = getDrawerActions(
            drawerViewModel = drawerViewModel,
            open = open,
            openAndPopUp = openAndPopUp
        ),
        navigationBarActions = getNavigationBarActions(
            navigationBarViewModel = navBarViewModel,
            open = open
        )
    )
}

fun addTimerScreen(
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions
) {
    SecondaryScreenTemplate(
        title = ,
        popUp = { /*TODO*/ }) {

    }
}