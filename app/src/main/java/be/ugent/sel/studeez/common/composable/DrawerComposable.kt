package be.ugent.sel.studeez.common.composable

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Drawer() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Studeez")
        Divider()
        Text(text = "Logout")
        IconTextButton(icon = Icons.Default.Home, text = "Home") {
            
        }
    }
}

@Composable
fun IconTextButton(icon: ImageVector, text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = icon, contentDescription = text)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
    Drawer()
}

@Preview
@Composable
fun DrawerEntryPreview() {
    IconTextButton(
        icon = Icons.Default.Home,
        text = "Home",
        onClick = {
            // Do something when the button is clicked
        }
    )
}