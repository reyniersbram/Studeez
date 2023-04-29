package be.ugent.sel.studeez.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.R
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
                items(subjects.value) {
                    SubjectEntry(subject = it)
                }
            }
            AddSubjectButton(onClick = addSubject)
        }
    }
}

@Composable
fun AddSubjectButton(
    onClick: () -> Unit,
) {
    Button(onClick = onClick) {
        Text(text = "Add Subject")
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