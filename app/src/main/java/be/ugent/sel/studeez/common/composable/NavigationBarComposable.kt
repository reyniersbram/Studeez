package be.ugent.sel.studeez.common.composable

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
import be.ugent.sel.studeez.ui.theme.StudeezTheme

@Composable
fun NavigationBar() {
    // TODO Pass functions and new screens.
    // TODO Pass which screen is selected.
    // TODO Disabled -> HIGH/MEDIUM_EMPHASIS if the page is implemented
    BottomNavigation(
        elevation = 10.dp
    ) {
        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.List, "Home") },
            label = { Text(text = "Home") },
            selected = false, // TODO
            onClick = { /* TODO */ }
        )

        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Check, "Tasks") },
            label = { Text(text = "Tasks") },
            selected = false, // TODO
            onClick = { /* TODO */ }
        )

        // Hack to space the entries in the navigation bar, make space for fab
        BottomNavigationItem(icon = {}, onClick = {}, selected = false)

        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Outlined.DateRange, "Sessions") },
            label = { Text(text = "Sessions") },
            selected = false, // TODO
            onClick = { /* TODO */ }
        )

        BottomNavigationItem(
            icon = { Icon(imageVector = Icons.Default.Person, "Profile") },
            label = { Text(text = "Profile") },
            selected = false, // TODO
            onClick = { /* TODO */ }
        )

    }
}

@Preview(showBackground = true)
@Composable
fun NavigationBarPreview() {
    StudeezTheme { NavigationBar() }
}