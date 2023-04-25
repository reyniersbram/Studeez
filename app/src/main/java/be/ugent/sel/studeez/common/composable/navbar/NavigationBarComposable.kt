package be.ugent.sel.studeez.common.composable.navbar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.navigation.StudeezDestinations.HOME_SCREEN
import be.ugent.sel.studeez.navigation.StudeezDestinations.PROFILE_SCREEN
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import be.ugent.sel.studeez.R.string as AppText

data class NavigationBarActions(
    val selectedTab: (String) -> Boolean,
    val onHomeClick: () -> Unit,
    val onTasksClick: () -> Unit,
    val onSessionsClick: () -> Unit,
    val onProfileClick: () -> Unit,
)

fun getNavigationBarActions(
    navigationBarViewModel: NavigationBarViewModel,
    open: (String) -> Unit,
): NavigationBarActions {
    return NavigationBarActions(
        selectedTab = { navigationBarViewModel.isSelected(it) },
        onHomeClick = {
            navigationBarViewModel.onHomeClick(open)
        },
        onTasksClick = {
            navigationBarViewModel.onTasksClick(open)
        },
        onSessionsClick = {
            navigationBarViewModel.onSessionsClick(open)
        },
        onProfileClick = {
            navigationBarViewModel.onProfileClick(open)
        },
    )
}

@Composable
fun NavigationBar(
    navigationBarActions: NavigationBarActions,
) {
    BottomNavigation(
        elevation = 10.dp
    ) {

        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.List, resources().getString(AppText.home)) },
            label = { Text(text = resources().getString(AppText.home)) },
            selected = navigationBarActions.selectedTab(HOME_SCREEN),
            onClick = navigationBarActions.onHomeClick
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Check, resources().getString(AppText.tasks)
                )
            },
            label = { Text(text = resources().getString(AppText.tasks)) },
            // TODO selected = navigationBarActions.selectedTab(TASKS_SCREEN),
            selected = false,
            onClick = navigationBarActions.onTasksClick
        )

        // Hack to space the entries in the navigation bar, make space for fab
        BottomNavigationItem(icon = {}, onClick = {}, selected = false)

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.DateRange, resources().getString(AppText.sessions)
                )
            },
            label = { Text(text = resources().getString(AppText.sessions)) },
            // TODO selected = navigationBarActions.selectedTab(SESSIONS_SCREEN),
            selected = false,
            onClick = navigationBarActions.onSessionsClick
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person, resources().getString(AppText.profile)
                )
            },
            label = { Text(text = resources().getString(AppText.profile)) },
            selected = navigationBarActions.selectedTab(PROFILE_SCREEN),
            onClick = navigationBarActions.onProfileClick
        )

    }
}

@Preview(showBackground = true)
@Composable
fun NavigationBarPreview() {
    StudeezTheme {
        NavigationBar(NavigationBarActions({ false }, {}, {}, {}, {}))
    }
}