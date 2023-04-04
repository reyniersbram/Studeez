package be.ugent.sel.studeez.common.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.ui.theme.StudeezTheme

@Composable
fun CollapsedAddButton() {
    FloatingActionButton(
        onClick = { /* TODO popup add options */ }
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "fab")
    }
}

@Composable
fun ExpandedAddButton() {
    Row() {
        IconButton(onClick = { /* TODO Go to next step */ }) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Task")
                Text(text = "Task")
            }
        }
        IconButton(onClick = { /* TODO Go to next step */ }) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Friend")
                Text(text = "Friend")
            }
        }
        IconButton(onClick = { /* TODO Go to next step */ }) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Session")
                Text(text = "Session")
            }
        }
    }
}

@Preview
@Composable
fun CollapsedAddButtonPreview() {
    StudeezTheme { CollapsedAddButton() }
}

@Preview
@Composable
fun ExpandedAddButtonPreview() {
    StudeezTheme { ExpandedAddButton() }
}