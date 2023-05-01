package be.ugent.sel.studeez.common.composable

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
enum class MultiFloatingState {
    Expanded,
    Collapsed
}

data class AddButtonActions(
    val onTaskClick: () -> Unit,
    val onFriendClick: () -> Unit,
    val onSessionClick: () -> Unit
)

@Composable
fun AddButton(
    addButtonActions: AddButtonActions
) {
    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.Collapsed)
    }

    // Rotate the button when expanded, normal when collapsed.
    val transition = updateTransition(targetState = multiFloatingState, label = TRANSITION)
    val rotate by transition.animateFloat(label = TRANSITION) {
        when (it) {
            MultiFloatingState.Expanded -> 315f
            MultiFloatingState.Collapsed -> 0f
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Show minis when expanded.
        if (multiFloatingState == MultiFloatingState.Expanded) {
            ExpandedAddButton(
                addButtonActions = addButtonActions
            )
        }

        // The base add button
        FloatingActionButton(
            onClick = {
                // Toggle expanded/collapsed.
                multiFloatingState = when (transition.currentState) {
                    MultiFloatingState.Collapsed -> MultiFloatingState.Expanded
                    MultiFloatingState.Expanded -> MultiFloatingState.Collapsed
                }
            }
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