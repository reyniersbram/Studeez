package be.ugent.sel.studeez.screens.tasks

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.data.local.models.task.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun TaskRoute(
    viewModel: TaskViewModel,
) {
    TaskScreen(
        addTask = viewModel::addTask,
        getTasks = viewModel::getTasks,
    )
}

@Composable
fun TaskScreen(
    addTask: () -> Unit,
    getTasks: () -> Flow<List<Task>>,
) {

}

@Preview
@Composable
fun TaskScreenPreview() {
    TaskScreen(
        addTask = {},
        getTasks = { flowOf() }
    )
}