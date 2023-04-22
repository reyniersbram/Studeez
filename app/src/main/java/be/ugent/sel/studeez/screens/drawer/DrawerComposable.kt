package be.ugent.sel.studeez.screens.drawer

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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme


@Composable
fun Drawer(
    onHomeButtonClick: () -> Unit,
    onTimersClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onAboutClick: () -> Unit,
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
                onClick = onHomeButtonClick,
            )
            DrawerEntry(
                icon = ImageVector.vectorResource(id = R.drawable.ic_timer),
                text = resources().getString(R.string.timers),
                onClick = onTimersClick,
            )
            DrawerEntry(
                icon = Icons.Default.Settings,
                text = resources().getString(R.string.settings),
                onClick = onSettingsClick,
            )
            DrawerEntry(
                icon = ImageVector.vectorResource(id = R.drawable.ic_logout),
                text = resources().getString(R.string.log_out),
                onClick = onLogoutClick,
            )
        }

        DrawerEntry(
            icon = Icons.Outlined.Info,
            text = resources().getString(R.string.about),
            onClick = onAboutClick,
        )
    }
}

@Composable
fun DrawerEntry(
    icon: ImageVector, text: String, onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = { onClick() })
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
    StudeezTheme {
        Drawer({}, {}, {}, {}, {})
    }
}