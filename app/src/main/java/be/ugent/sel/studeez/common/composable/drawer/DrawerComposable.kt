package be.ugent.sel.studeez.common.composable.drawer

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme

data class DrawerActions(
    val onHomeButtonClick: () -> Unit,
    val onTimersClick: () -> Unit,
    val onSettingsClick: () -> Unit,
    val onLogoutClick: () -> Unit,
    val onAboutClick: (Context) -> Unit,
)

fun getDrawerActions(
    drawerViewModel: DrawerViewModel,
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
): DrawerActions {
    return DrawerActions(
        onHomeButtonClick = { drawerViewModel.onHomeButtonClick(open) },
        onTimersClick = { drawerViewModel.onTimersClick(open) },
        onSettingsClick = { drawerViewModel.onSettingsClick(open) },
        onLogoutClick = { drawerViewModel.onLogoutClick(openAndPopUp) },
        onAboutClick = { context -> drawerViewModel.onAboutClick(open, context = context) },
    )
}

@Composable
fun Drawer(
    drawerActions: DrawerActions,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            DrawerEntry(
                icon = Icons.Default.Home,
                text = resources().getString(R.string.home),
                onClick = drawerActions.onHomeButtonClick,
            )
            DrawerEntry(
                icon = ImageVector.vectorResource(id = R.drawable.ic_timer),
                text = resources().getString(R.string.timers),
                onClick = drawerActions.onTimersClick,
            )
            DrawerEntry(
                icon = Icons.Default.Settings,
                text = resources().getString(R.string.settings),
                onClick = drawerActions.onSettingsClick,
            )
            DrawerEntry(
                icon = ImageVector.vectorResource(id = R.drawable.ic_logout),
                text = resources().getString(R.string.log_out),
                onClick = drawerActions.onLogoutClick,
            )
        }

        val context = LocalContext.current
        DrawerEntry(
            icon = Icons.Outlined.Info,
            text = resources().getString(R.string.about),
            onClick = { drawerActions.onAboutClick(context) },
        )
    }
}

@Composable
fun DrawerEntry(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(0.15f)
        ) {
            Icon(imageVector = icon, contentDescription = text)
        }
        Box(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(0.85f)
        ) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
    val drawerActions = DrawerActions({}, {}, {}, {}, {})
    StudeezTheme {
        Drawer(drawerActions)
    }
}