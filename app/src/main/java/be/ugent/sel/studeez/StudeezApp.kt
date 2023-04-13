package be.ugent.sel.studeez

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.home.HomeScreen
import be.ugent.sel.studeez.screens.log_in.LoginScreen
import be.ugent.sel.studeez.screens.sign_up.SignUpScreen
import be.ugent.sel.studeez.screens.splash.SplashScreen
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
                NavHost(
                    navController = appState.navController,
                    startDestination = StudeezDestinations.SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    studeezGraph(appState)
                }
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

fun NavGraphBuilder.studeezGraph(appState: StudeezAppstate) {

    composable(StudeezDestinations.SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(StudeezDestinations.LOGIN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(StudeezDestinations.SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(StudeezDestinations.HOME_SCREEN) {
        HomeScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }
}