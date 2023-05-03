package be.ugent.sel.studeez.common.composable.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.resources

@Composable
fun TaskEntry(
    task: Task,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
    ) {
        val color = if (task.completed) Color.Gray else Color.Black
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(22f),
            ) {
                Checkbox(
                    checked = task.completed,
                    onCheckedChange = {},
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Gray,
                        uncheckedColor = MaterialTheme.colors.onSurface,
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = task.name,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        color = color,
                        modifier = Modifier.weight(13f),
                    )
                    Text(
                        text = "${HoursMinutesSeconds(task.time)}",
                        color = color,
                        modifier = Modifier.weight(5f)
                    )

                }
            }
            Box(modifier = Modifier.weight(7f)) {
                if (task.completed) {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(start = 20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = resources().getString(R.string.delete_task),
                        )
                    }
                } else {
                    StealthButton(
                        text = R.string.start,
                        modifier = Modifier
                            .padding(end = 5.dp),
                    ) {
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskEntryPreview() {
    TaskEntry(
        task = Task(
            name = "Test Task",
            completed = false,
        ),
    )
}

@Preview
@Composable
fun CompletedTaskEntryPreview() {
    TaskEntry(
        task = Task(
            name = "Test Task",
            completed = true,
        )
    )
}

@Preview
@Composable
fun OverflowTaskEntryPreview() {
    TaskEntry(
        task = Task(
            name = "Test Taskkkkkkkkkkkkkkkkkkkkkkkkkkk",
            completed = false,
        ),
    )
}