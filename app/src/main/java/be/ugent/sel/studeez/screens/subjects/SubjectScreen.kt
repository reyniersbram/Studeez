package be.ugent.sel.studeez.screens.subjects

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.common.composable.NewTaskSubjectButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.common.composable.tasks.SubjectEntry
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.navigation.StudeezDestinations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun SubjectRoute(
    open: (String) -> Unit,
    viewModel: SubjectViewModel,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {
    val uiState by viewModel.uiState.collectAsState()
    SubjectScreen(
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        onAddSubject = { viewModel.onAddSubject(open) },
        onViewSubject = { viewModel.onSelectSubject(it) { open(StudeezDestinations.TASKS_SCREEN) } },
        getTaskCount = viewModel::getTaskCount,
        getCompletedTaskCount = viewModel::getCompletedTaskCount,
        getStudyTime = viewModel::getStudyTime,
        uiState = uiState,
    )
}

@Composable
fun SubjectScreen(
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
    onAddSubject: () -> Unit,
    onViewSubject: (Subject) -> Unit,
    getTaskCount: (Subject) -> Flow<Int>,
    getCompletedTaskCount: (Subject) -> Flow<Int>,
    getStudyTime: (Subject) -> Flow<Int>,
    uiState: SubjectUiState,
) {
    PrimaryScreenTemplate(
        title = stringResource(AppText.my_subjects),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        barAction = {},
    ) {
        when (uiState) {
            SubjectUiState.Loading -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
            }
            is SubjectUiState.Succes -> {
                Column(
                    modifier = Modifier.padding(top = 5.dp)
                ) {
                    NewTaskSubjectButton(onClick = onAddSubject, AppText.new_subject)
                    LazyColumn {
                        items(uiState.subjects) { subject ->
                            SubjectEntry(
                                subject = subject,
                                getTaskCount = { getTaskCount(subject) },
                                getCompletedTaskCount = { getCompletedTaskCount(subject) },
                                getStudyTime = { getStudyTime(subject) },
                            ) {
                                StealthButton(
                                    text = AppText.view_tasks,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 5.dp)
                                        .weight(1f)
                                ) {
                                    onViewSubject(subject)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SubjectScreenPreview() {
    SubjectScreen(
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({ false }, {}, {}, {}, {}, {}, {}, {}),
        onAddSubject = {},
        onViewSubject = {},
        getTaskCount = { flowOf() },
        getCompletedTaskCount = { flowOf() },
        getStudyTime = { flowOf() },
        uiState = SubjectUiState.Succes(
            listOf(
                Subject(
                    name = "Test Subject",
                    argb_color = 0xFFFFD200,
                )
            )
        )
    )
}

@Preview
@Composable
fun SubjectScreenLoadingPreview() {
    SubjectScreen(
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({ false }, {}, {}, {}, {}, {}, {}, {}),
        onAddSubject = {},
        onViewSubject = {},
        getTaskCount = { flowOf() },
        getCompletedTaskCount = { flowOf() },
        getStudyTime = { flowOf() },
        uiState = SubjectUiState.Loading,
    )
}