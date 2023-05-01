package be.ugent.sel.studeez

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.drawer.DrawerViewModel
import be.ugent.sel.studeez.common.composable.drawer.getDrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarViewModel
import be.ugent.sel.studeez.common.composable.navbar.getNavigationBarActions
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.home.HomeRoute
import be.ugent.sel.studeez.screens.log_in.LoginRoute
import be.ugent.sel.studeez.screens.profile.EditProfileRoute
import be.ugent.sel.studeez.screens.profile.ProfileRoute
import be.ugent.sel.studeez.screens.session.SessionRoute
import be.ugent.sel.studeez.screens.session_recap.SessionRecapRoute
import be.ugent.sel.studeez.screens.sign_up.SignUpRoute
import be.ugent.sel.studeez.screens.splash.SplashRoute
import be.ugent.sel.studeez.screens.timer_overview.TimerOverviewRoute
import be.ugent.sel.studeez.screens.timer_selection.TimerSelectionRoute
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun StudeezApp() {
    StudeezTheme {
        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                StudeezNavGraph(appState, Modifier.padding(innerPaddingModifier))
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        StudeezAppstate(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Composable
fun StudeezNavGraph(
    appState: StudeezAppstate,
    modifier: Modifier = Modifier,
) {
    val drawerViewModel: DrawerViewModel = hiltViewModel()
    val navBarViewModel: NavigationBarViewModel = hiltViewModel()

    val backStackEntry by appState.navController.currentBackStackEntryAsState()
    val getCurrentScreen: () -> String? = { backStackEntry?.destination?.route }

    val goBack: () -> Unit = { appState.popUp() }
    val open: (String) -> Unit = { appState.navigate(it) }
    val openAndPopUp: (String, String) -> Unit =
        { route, popUp -> appState.navigateAndPopUp(route, popUp) }

    val drawerActions: DrawerActions = getDrawerActions(drawerViewModel, open, openAndPopUp)
    val navigationBarActions: NavigationBarActions =
        getNavigationBarActions(navBarViewModel, open, getCurrentScreen)

    NavHost(
        navController = appState.navController,
        startDestination = StudeezDestinations.SPLASH_SCREEN,
        modifier = modifier,
    ) {
        // NavBar
        composable(StudeezDestinations.HOME_SCREEN) {
            HomeRoute(
                open,
                viewModel = hiltViewModel(),
                drawerActions = drawerActions,
                navigationBarActions = navigationBarActions,
            )
        }

        composable(StudeezDestinations.TASKS_SCREEN) {
            // TODO
        }

        composable(StudeezDestinations.SESSIONS_SCREEN) {
            // TODO
        }

        composable(StudeezDestinations.PROFILE_SCREEN) {
            ProfileRoute(
                open,
                viewModel = hiltViewModel(),
                drawerActions = drawerActions,
                navigationBarActions = navigationBarActions,
            )
        }

        // Drawer
        composable(StudeezDestinations.TIMER_SCREEN) {
            TimerOverviewRoute(
                viewModel = hiltViewModel(),
                drawerActions = drawerActions,
            )
        }

        composable(StudeezDestinations.SETTINGS_SCREEN) {
            // TODO
        }

        // Login flow
        composable(StudeezDestinations.SPLASH_SCREEN) {
            SplashRoute(
                openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }

        composable(StudeezDestinations.LOGIN_SCREEN) {
            LoginRoute(
                openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }

        composable(StudeezDestinations.SIGN_UP_SCREEN) {
            SignUpRoute(
                openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }

        // Studying flow
        composable(StudeezDestinations.TIMER_SELECTION_SCREEN) {
            TimerSelectionRoute(
                open,
                goBack,
                viewModel = hiltViewModel(),
            )
        }

        composable(StudeezDestinations.SESSION_SCREEN) {
            SessionRoute(
                open,
                openAndPopUp,
                viewModel = hiltViewModel()
            )
        }

        composable(StudeezDestinations.SESSION_RECAP) {
            SessionRecapRoute(
                openAndPopUp = openAndPopUp,
                viewModel = hiltViewModel()
            )
        }

        // Edit screens
        composable(StudeezDestinations.EDIT_PROFILE_SCREEN) {
            EditProfileRoute(
                goBack,
                openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }
    }
}
