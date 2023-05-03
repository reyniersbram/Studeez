package be.ugent.sel.studeez.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.NewTaskSubjectButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.common.composable.tasks.SubjectEntry
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SubjectRoute(
    open: (String) -> Unit,
    viewModel: SubjectViewModel,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {
    SubjectScreen(
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        addSubject = { viewModel.addSubject(open) },
        getSubjects = viewModel::getSubjects,
        onViewSubject = { viewModel.onViewSubject(it, open) },
    )
}

@Composable
fun SubjectScreen(
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    addSubject: () -> Unit,
    getSubjects: () -> Flow<List<Subject>>,
    onViewSubject: (Subject) -> Unit,
) {
    PrimaryScreenTemplate(
        title = resources().getString(R.string.tasks),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        barAction = {},
    ) {
        val subjects = getSubjects().collectAsState(initial = emptyList())
        Column(
            modifier = Modifier.padding(top = 5.dp)
        ) {
            LazyColumn {
                items(subjects.value) {
                    SubjectEntry(
                        subject = it,
                        onViewSubject = { onViewSubject(it) },
                    )
                }
            }
            NewTaskSubjectButton(onClick = addSubject, R.string.new_subject)
        }
    }
}

@Preview
@Composable
fun SubjectScreenPreview() {
    SubjectScreen(
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({ false }, {}, {}, {}, {}),
        addSubject = {},
        getSubjects = { flowOf() },
        onViewSubject = {},
    )
}