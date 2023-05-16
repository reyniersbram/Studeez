package be.ugent.sel.studeez.screens.subjects.select

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
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.composable.tasks.SubjectEntry
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.navigation.StudeezDestinations
import be.ugent.sel.studeez.screens.subjects.SubjectUiState
import be.ugent.sel.studeez.screens.subjects.SubjectViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SubjectSelectionRoute(
    open: (String) -> Unit,
    goBack: () -> Unit,
    viewModel: SubjectViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    SubjectSelectionScreen(
        onViewSubject = { viewModel.onSelectSubject(it) { open(StudeezDestinations.ADD_TASK_FORM) } },
        getTaskCount = viewModel::getTaskCount,
        getCompletedTaskCount = viewModel::getCompletedTaskCount,
        getStudyTime = viewModel::getStudyTime,
        goBack = goBack,
        uiState = uiState,
    )
}

@Composable
fun SubjectSelectionScreen(
    goBack: () -> Unit,
    onViewSubject: (Subject) -> Unit,
    getTaskCount: (Subject) -> Flow<Int>,
    getCompletedTaskCount: (Subject) -> Flow<Int>,
    getStudyTime: (Subject) -> Flow<Int>,
    uiState: SubjectUiState,
) {
    SecondaryScreenTemplate(
        title = stringResource(R.string.select_subject_title),
        barAction = {},
        popUp = goBack,
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
                    LazyColumn {
                        items(uiState.subjects) { subject ->
                            SubjectEntry(
                                subject = subject,
                                getTaskCount = { getTaskCount(subject) },
                                getCompletedTaskCount = { getCompletedTaskCount(subject) },
                                getStudyTime = { getStudyTime(subject) },
                            ) {
                                StealthButton(
                                    text = R.string.select_subject,
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp)
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
    SubjectSelectionScreen(
        goBack = {},
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
    SubjectSelectionScreen(
        goBack = {},
        onViewSubject = {},
        getTaskCount = { flowOf() },
        getCompletedTaskCount = { flowOf() },
        getStudyTime = { flowOf() },
        uiState = SubjectUiState.Loading,
    )
}