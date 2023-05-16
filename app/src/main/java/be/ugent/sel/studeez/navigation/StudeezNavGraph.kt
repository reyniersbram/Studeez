package be.ugent.sel.studeez.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import be.ugent.sel.studeez.StudeezAppstate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.drawer.DrawerViewModel
import be.ugent.sel.studeez.common.composable.drawer.getDrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarViewModel
import be.ugent.sel.studeez.common.composable.navbar.getNavigationBarActions
import be.ugent.sel.studeez.screens.friends.friends_overview.FriendsOveriewRoute
import be.ugent.sel.studeez.screens.friends.friends_search.SearchFriendsRoute
import be.ugent.sel.studeez.screens.friends_feed.FriendsFeedRoute
import be.ugent.sel.studeez.screens.friends_feed.FriendsFeedScreen
import be.ugent.sel.studeez.screens.home.HomeRoute
import be.ugent.sel.studeez.screens.log_in.LoginRoute
import be.ugent.sel.studeez.screens.profile.edit_profile.EditProfileRoute
import be.ugent.sel.studeez.screens.profile.ProfileRoute
import be.ugent.sel.studeez.screens.profile.public_profile.PublicProfileRoute
import be.ugent.sel.studeez.screens.session.SessionRoute
import be.ugent.sel.studeez.screens.session_recap.SessionRecapRoute
import be.ugent.sel.studeez.screens.settings.SettingsRoute
import be.ugent.sel.studeez.screens.sign_up.SignUpRoute
import be.ugent.sel.studeez.screens.splash.SplashRoute
import be.ugent.sel.studeez.screens.subjects.SubjectRoute
import be.ugent.sel.studeez.screens.subjects.form.SubjectCreateRoute
import be.ugent.sel.studeez.screens.subjects.form.SubjectEditRoute
import be.ugent.sel.studeez.screens.tasks.TaskRoute
import be.ugent.sel.studeez.screens.tasks.form.TaskCreateRoute
import be.ugent.sel.studeez.screens.tasks.form.TaskEditRoute
import be.ugent.sel.studeez.screens.timer_form.TimerAddRoute
import be.ugent.sel.studeez.screens.timer_form.TimerEditRoute
import be.ugent.sel.studeez.screens.timer_form.timer_type_select.TimerTypeSelectScreen
import be.ugent.sel.studeez.screens.timer_overview.TimerOverviewRoute
import be.ugent.sel.studeez.screens.timer_selection.TimerSelectionRoute

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
    val clearAndNavigate: (route: String) -> Unit = { route -> appState.clearAndNavigate(route) }

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
                drawerActions = drawerActions,
                navigationBarActions = navigationBarActions,
                feedViewModel = hiltViewModel(),
                viewModel = hiltViewModel()
            )
        }

        composable(StudeezDestinations.SUBJECT_SCREEN) {
            SubjectRoute(
                open = open,
                viewModel = hiltViewModel(),
                drawerActions = drawerActions,
                navigationBarActions = navigationBarActions,
            )
        }

        composable(StudeezDestinations.ADD_SUBJECT_FORM) {
            SubjectCreateRoute(
                goBack = goBack,
                openAndPopUp = openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }

        composable(StudeezDestinations.EDIT_SUBJECT_FORM) {
            SubjectEditRoute(
                goBack = goBack,
                openAndPopUp = openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }

        composable(StudeezDestinations.TASKS_SCREEN) {
            TaskRoute(
                goBack = goBack,
                open = open,
                viewModel = hiltViewModel(),
            )
        }

        composable(StudeezDestinations.ADD_TASK_FORM) {
            TaskCreateRoute(
                goBack = goBack,
                openAndPopUp = openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }

        composable(StudeezDestinations.EDIT_TASK_FORM) {
            TaskEditRoute(
                goBack = goBack,
                openAndPopUp = openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }


        composable(StudeezDestinations.SESSIONS_SCREEN) {
            FriendsFeedRoute(
                drawerActions = drawerActions,
                navigationBarActions = navigationBarActions,
                viewModel = hiltViewModel()
            )
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
                open = open
            )
        }

        composable(StudeezDestinations.SETTINGS_SCREEN) {
            SettingsRoute(
                drawerActions = drawerActions
            )
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

        composable(StudeezDestinations.TIMER_TYPE_CHOOSING_SCREEN) {
            TimerTypeSelectScreen(
                open = open,
                popUp = goBack
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
                clearAndNavigate = clearAndNavigate,
                viewModel = hiltViewModel()
            )
        }

        composable(StudeezDestinations.ADD_TIMER_SCREEN) {
            TimerAddRoute(
                popUp = goBack,
                viewModel = hiltViewModel()
            )
        }

        composable(StudeezDestinations.TIMER_EDIT_SCREEN) {
            TimerEditRoute(
                popUp = goBack,
                viewModel = hiltViewModel()
            )
        }

        // Friends flow
        composable(StudeezDestinations.FRIENDS_OVERVIEW_SCREEN) {
            FriendsOveriewRoute(
                open = open,
                popUp = goBack,
                viewModel = hiltViewModel()
            )
        }

        composable(StudeezDestinations.SEARCH_FRIENDS_SCREEN) {
            SearchFriendsRoute(
                popUp = goBack,
                open = open,
                viewModel = hiltViewModel()
            )
        }

        composable(StudeezDestinations.PUBLIC_PROFILE_SCREEN) {
            PublicProfileRoute(
                popUp = goBack,
                open = open,
                viewModel = hiltViewModel()
            )
        }

        // Create & edit screens
        composable(StudeezDestinations.CREATE_TASK_SCREEN) {
            // TODO
        }

        composable(StudeezDestinations.CREATE_SESSION_SCREEN) {
            // TODO
        }

        composable(StudeezDestinations.EDIT_PROFILE_SCREEN) {
            EditProfileRoute(
                goBack,
                openAndPopUp,
                viewModel = hiltViewModel(),
            )
        }
    }
}

