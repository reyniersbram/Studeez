package be.ugent.sel.studeez.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.NewTaskSubjectButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.SubjectEntry
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun TaskRoute(
    open: (String) -> Unit,
    viewModel: TaskViewModel,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {
    TaskScreen(
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        addSubject = { viewModel.addSubject() },
    ) { viewModel.getSubjects() }
}

@Composable
fun TaskScreen(
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    addSubject: () -> Unit,
    getSubjects: () -> Flow<List<Subject>>,
) {
    PrimaryScreenTemplate(
        title = resources().getString(R.string.tasks),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        barAction = {},
    ) {
        val subjects = getSubjects().collectAsState(initial = emptyList())
        Column {
            LazyColumn {
                if (subjects.value.isNotEmpty()) {
                    item {
                        SubjectEntry(subject = subjects.value[0])
                    }
                }
                if (subjects.value.size > 1) {
                    items(subjects.value.subList(1, subjects.value.lastIndex + 1)) {
                        Column {
                            Divider(modifier = Modifier.padding(10.dp, 0.dp))
                            SubjectEntry(subject = it)
                        }
                    }
                }
            }
            NewTaskSubjectButton(onClick = addSubject, R.string.new_subject)
        }
    }
}

@Preview
@Composable
fun TaskScreenPreview() {
    TaskScreen(
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({ false }, {}, {}, {}, {}),
        {},
    ) { flowOf() }
}