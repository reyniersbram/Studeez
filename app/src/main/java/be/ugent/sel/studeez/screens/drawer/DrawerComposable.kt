package be.ugent.sel.studeez.screens.drawer

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme


@Composable
fun Drawer(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: DrawerViewModel = hiltViewModel()
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            DrawerEntry(
                icon = Icons.Default.Home,
                text = resources().getString(R.string.home)
            ) {
                viewModel.onHomeButtonClick(open)
            }
            DrawerEntry(
                icon = ImageVector.vectorResource(id = R.drawable.ic_timer),
                text = resources().getString(R.string.timers)
            ) {
                viewModel.onTimersClick(open)
            }
            DrawerEntry(
                icon = Icons.Default.Settings,
                text = resources().getString(R.string.settings)
            ) {
                viewModel.onSettingsClick(open)
            }
            DrawerEntry(
                icon = ImageVector.vectorResource(id = R.drawable.ic_logout),
                text = resources().getString(R.string.log_out)
            ) {
                viewModel.onLogoutClick(openAndPopUp)
            }
        }

        DrawerEntry(
            icon = Icons.Outlined.Info,
            text = resources().getString(R.string.about)
        ) {
            viewModel.onAboutClick(open)
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
        Drawer(
            { _, -> {} },
            { _, _ -> {} },
            hiltViewModel()
        )
    }
}