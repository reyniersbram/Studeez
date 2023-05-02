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
import androidx.navigation.compose.rememberNavController
import be.ugent.sel.studeez.common.composable.drawer.DrawerViewModel
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarViewModel
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.home.HomeRoute
import be.ugent.sel.studeez.screens.log_in.LoginRoute
import be.ugent.sel.studeez.screens.profile.EditProfileRoute
import be.ugent.sel.studeez.screens.profile.ProfileRoute
import be.ugent.sel.studeez.screens.session.SessionRoute
import be.ugent.sel.studeez.screens.sign_up.SignUpRoute
import be.ugent.sel.studeez.screens.splash.SplashRoute
import be.ugent.sel.studeez.screens.timer_overview.TimerOverviewRoute
import be.ugent.sel.studeez.screens.timer_overview.add_timer.AddTimerRoute
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
    modifier: Modifier,
) {
    val drawerViewModel: DrawerViewModel = hiltViewModel()
    val navBarViewModel: NavigationBarViewModel = hiltViewModel()

    NavHost(
        navController = appState.navController,
        startDestination = StudeezDestinations.SPLASH_SCREEN,
        modifier = modifier,
    ) {
        val goBack: () -> Unit = {
            appState.popUp()
        }

        val open: (String) -> Unit = { route ->
            appState.navigate(route)
        }

        val openAndPopUp: (String, String) -> Unit = { route, popUp ->
            appState.navigateAndPopUp(route, popUp)
        }


        composable(StudeezDestinations.SPLASH_SCREEN) {
            SplashRoute(openAndPopUp, viewModel = hiltViewModel())
        }

        composable(StudeezDestinations.LOGIN_SCREEN) {
            LoginRoute(openAndPopUp, viewModel = hiltViewModel())
        }

        composable(StudeezDestinations.SIGN_UP_SCREEN) {
            SignUpRoute(openAndPopUp, viewModel = hiltViewModel())
        }

        composable(StudeezDestinations.HOME_SCREEN) {
            HomeRoute(
                open,
                openAndPopUp,
                viewModel = hiltViewModel(),
                drawerViewModel = drawerViewModel,
                navBarViewModel = navBarViewModel,
            )
        }

        // TODO Tasks screen
        // TODO Sessions screen

        composable(StudeezDestinations.PROFILE_SCREEN) {
            ProfileRoute(open, openAndPopUp, viewModel = hiltViewModel())
        }

        composable(StudeezDestinations.TIMER_OVERVIEW_SCREEN) {
            TimerOverviewRoute(
                open,
                openAndPopUp,
                viewModel = hiltViewModel(),
                drawerViewModel = drawerViewModel,
                navBarViewModel = navBarViewModel,
            )
        }

        composable(StudeezDestinations.SESSION_SCREEN) {
            SessionRoute(open, viewModel = hiltViewModel())
        }

        // TODO Timers screen
        // TODO Settings screen

        // Edit screens
        composable(StudeezDestinations.EDIT_PROFILE_SCREEN) {
            EditProfileRoute(goBack, openAndPopUp, viewModel = hiltViewModel())
        }

        composable(StudeezDestinations.TIMER_SELECTION_SCREEN) {
            TimerSelectionRoute(
                open,
                openAndPopUp,
                viewModel = hiltViewModel(),
                drawerViewModel = drawerViewModel,
                navBarViewModel = navBarViewModel,
            )
        }

        composable(StudeezDestinations.ADD_TIMER_SCREEN) {
            AddTimerRoute(
                open = open,
                goBack = goBack,
                viewModel = hiltViewModel()
            )
        }
    }
}
