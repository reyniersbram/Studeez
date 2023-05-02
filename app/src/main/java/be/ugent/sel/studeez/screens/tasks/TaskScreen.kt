package be.ugent.sel.studeez.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.NewTaskSubjectButton
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TaskActions(
    val addTask: () -> Unit,
    val getSubject: () -> Subject,
    val getTasks: () -> Flow<List<Task>>,
    val deleteSubject: () -> Unit,
)

fun getTaskActions(viewModel: TaskViewModel): TaskActions {
    return TaskActions(
        addTask = viewModel::addTask,
        getTasks = viewModel::getTasks,
        getSubject = { Subject(name = "Test Subject") },
        deleteSubject = {},
    )
}

@Composable
fun TaskRoute(
    goBack: () -> Unit,
    viewModel: TaskViewModel,
) {
    TaskScreen(
        goBack = goBack,
        taskActions = getTaskActions(viewModel = viewModel),
    )
}

@Composable
fun TaskScreen(
    goBack: () -> Unit,
    taskActions: TaskActions,
) {
    SecondaryScreenTemplate(
        title = taskActions.getSubject().name,
        popUp = goBack,
        barAction = { EditAction {} } // TODO implement
    ) {
        val tasks = taskActions.getTasks().collectAsState(initial = emptyList())
        Column(
            modifier = Modifier.padding(top = 5.dp)
        ) {
            LazyColumn {
                items(tasks.value) {
                    Text(text = it.name)
                }
            }
            NewTaskSubjectButton(onClick = taskActions.addTask, R.string.new_task)
            BasicButton(
                text = R.string.delete_subject,
                modifier = Modifier.basicButton(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White,
                ),
                onClick = taskActions.deleteSubject,
            )
        }
    }
}

@Composable
fun EditAction(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = resources().getString(R.string.edit_task)
        )

    }
}

@Preview
@Composable
fun TaskScreenPreview() {
    TaskScreen(
        goBack = {},
        taskActions = TaskActions(
            {},
            { Subject(name = "Test Subject") },
            { flowOf() },
            {},
        )
    )
}