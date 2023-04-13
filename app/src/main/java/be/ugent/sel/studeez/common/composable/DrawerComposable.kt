package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.data.local.models.User
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme


@Composable
fun Drawer(
    onLogoutClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        DrawerEntry(
            icon = Icons.Default.Home,
            text = resources().getString(R.string.home)
        ) {
            // TODO Go to home
        }
        DrawerEntry(
            icon = ImageVector.vectorResource(id = R.drawable.ic_timer),
            text = resources().getString(R.string.timers)
        ) {
            // TODO Go to timers
        }
        DrawerEntry(
            icon = Icons.Default.Settings,
            text = resources().getString(R.string.settings)
        ) {
            // TODO Go to settings
        }
        DrawerEntry(
            icon = ImageVector.vectorResource(id = R.drawable.ic_logout),
            text = resources().getString(R.string.log_out)
        ) {
            onLogoutClick()
        }

        DrawerEntry(
            icon = Icons.Outlined.Info,
            text = resources().getString(R.string.about)
        ) {
            // TODO Go to about
        }
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
            .clickable(onClick = { onClick() })
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth(0.25f)) {
            Icon(imageVector = icon, contentDescription = text)
        }
        Box(modifier = Modifier.fillMaxWidth(0.75f)) {
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
    StudeezTheme {
        Drawer(
            {}
        )
    }
}