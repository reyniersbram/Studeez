package be.ugent.sel.studeez.common.composable

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.ui.theme.StudeezTheme
import be.ugent.sel.studeez.R.string as AppText

const val TRANSITION = "transition"
val HEIGHT_DIFFERENCE = 30.dp

data class AddButtonActions(
    val onTaskClick: () -> Unit,
    val onFriendClick: () -> Unit,
    val onSessionClick: () -> Unit
)

@Composable
fun AddButton(
    addButtonActions: AddButtonActions
) {
    var isExpanded by remember { mutableStateOf(false) }

    // Rotate the button when expanded, normal when collapsed.
    val transition = updateTransition(targetState = isExpanded, label = TRANSITION)
    val rotate by transition.animateFloat(label = TRANSITION) { expanded -> if (expanded) 315f else 0f }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box {
            // Show minis when expanded.
            if (isExpanded) {
                ExpandedAddButton(
                    addButtonActions = addButtonActions
                )
            }
        }

        // The base add button
        FloatingActionButton(
            onClick = {
                // Toggle expanded/collapsed.
                isExpanded = !isExpanded
            },
            modifier = Modifier.padding(bottom = if (isExpanded) 78.dp else 0.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "fab",
                modifier = Modifier.rotate(rotate) // The rotation
            )
        }
    }
}

@Composable
fun ExpandedAddButton(
    addButtonActions: AddButtonActions
) {
    Row {
        ExpandedEntry(
            name = AppText.task,
            imageVector = Icons.Default.Check,
            onClick = addButtonActions.onTaskClick,
            modifier = Modifier.padding(36.dp, HEIGHT_DIFFERENCE, 36.dp, 0.dp)
        )

        ExpandedEntry(
            name = AppText.friend,
            imageVector = Icons.Default.Person,
            onClick = addButtonActions.onFriendClick
        )

        ExpandedEntry(
            name = AppText.session,
            imageVector = Icons.Default.DateRange,
            onClick = addButtonActions.onSessionClick,
            modifier = Modifier.padding(36.dp, HEIGHT_DIFFERENCE, 36.dp, 0.dp)
        )
    }
}

@Composable
fun ExpandedEntry(
    name: Int,
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = imageVector,
                contentDescription = resources().getString(name),
                // TODO Dark overlay
                //  tint = colors.surface
            )
            Text(
                text = resources().getString(name),
                // TODO Dark overlay
                //  color = colors.surface
            )
        }

    }

}

@Preview
@Composable
fun AddButtonPreview() {
    StudeezTheme { AddButton(
        addButtonActions = AddButtonActions({}, {}, {})
    )}
}

@Preview
@Composable
fun ExpandedAddButtonPreview() {
    StudeezTheme { ExpandedAddButton (
        addButtonActions = AddButtonActions({}, {}, {})
    ) }
}