package be.ugent.sel.studeez.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.common.composable.NewTaskSubjectButton
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.composable.tasks.TaskEntry
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import be.ugent.sel.studeez.R.string as AppText

data class TaskActions(
    val addTask: () -> Unit,
    val getSubject: () -> Subject,
    val getTasks: () -> Flow<List<Task>>,
    val deleteTask: (Task) -> Unit,
    val onCheckTask: (Task, Boolean) -> Unit,
    val editSubject: () -> Unit,
    val startTask: (Task) -> Unit,
    val archiveTask: (Task) -> Unit,
)

fun getTaskActions(viewModel: TaskViewModel, open: (String) -> Unit): TaskActions {
    return TaskActions(
        addTask = { viewModel.addTask(open) },
        getTasks = viewModel::getTasks,
        getSubject = viewModel::getSelectedSubject,
        deleteTask = viewModel::deleteTask,
        onCheckTask = { task, isChecked -> viewModel.toggleTaskCompleted(task, isChecked) },
        editSubject = { viewModel.editSubject(open) },
        startTask = { task -> viewModel.startTask(task, open) },
        archiveTask = viewModel::archiveTask
    )
}

@Composable
fun TaskRoute(
    goBack: () -> Unit,
    open: (String) -> Unit,
    viewModel: TaskViewModel,
) {
    TaskScreen(
        goBack = goBack,
        taskActions = getTaskActions(viewModel = viewModel, open = open),
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
        barAction = { EditAction(onClick = taskActions.editSubject) }
    ) {
        val tasks = taskActions.getTasks().collectAsState(initial = emptyList())
        Column(
            modifier = Modifier.padding(top = 5.dp)
        ) {
            LazyColumn {
                items(tasks.value) {
                    TaskEntry(
                        task = it,
                        onCheckTask = { isChecked -> taskActions.onCheckTask(it, isChecked) },
                        onArchiveTask = { taskActions.archiveTask(it) },
                        onStartTask = { taskActions.startTask(it) }
                    )
                }
            }
            NewTaskSubjectButton(onClick = taskActions.addTask, AppText.new_task)
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
            contentDescription = stringResource(AppText.edit_task)
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
            { _, _ -> run {} },
            {},
            {},
            {},
        )
    )
}