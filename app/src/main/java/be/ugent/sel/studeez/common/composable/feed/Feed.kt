package be.ugent.sel.studeez.common.composable.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.ugent.sel.studeez.common.composable.DateText
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class FeedActions(
    val getFeedEntries: () -> Flow<Map<String, List<FeedEntry>>>,
    val continueTask: (String, String) -> Unit,
)

fun getFeedActions(
    viewmodel: FeedViewModel,
    open: (String) -> Unit,
): FeedActions {
    return FeedActions(
        getFeedEntries = viewmodel::getFeedEntries,
        continueTask = { subjectId, taskId ->
            viewmodel.continueTask(
                open,
                subjectId,
                taskId,
            )
        },
    )
}

@Composable
fun Feed(
    feedActions: FeedActions,
    uiState: FeedUiState,
) {
    when (uiState) {
        FeedUiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.onBackground)
            }
        }
        is FeedUiState.Succes -> {
//            val feedEntries = feedActions.getFeedEntries().collectAsState(initial = emptyMap())
            val feedEntries = uiState.feedEntries
            LazyColumn {
                items(feedEntries.toList()) { (date, feedEntries) ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val totalDayStudyTime: Int = feedEntries.sumOf { it.totalStudyTime }
                        DateText(date = date)
                        Text(
                            text = "${HoursMinutesSeconds(totalDayStudyTime)}",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    feedEntries.forEach { feedEntry ->
                        FeedEntry(feedEntry = feedEntry) {
                            feedActions.continueTask(feedEntry.subjectId, feedEntry.taskId)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun FeedPreview() {
    Feed(
        feedActions = FeedActions({ flowOf() }, { _, _ -> run {} }),
        uiState = FeedUiState.Loading,
    )
}