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

@Composable
fun Feed(
    uiState: FeedUiState,
    continueTask: (String, String) -> Unit,
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
                            continueTask(feedEntry.subjectId, feedEntry.taskId)
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
fun FeedLoadingPreview() {
    Feed(
        uiState = FeedUiState.Loading,
        continueTask = { _, _ -> run {} },
    )
}

@Preview
@Composable
fun FeedPreview() {
    Feed(
        uiState = FeedUiState.Succes(
            mapOf(
                "08 May 2023" to listOf(
                    FeedEntry(
                        argb_color = 0xFFFFD200,
                        subJectName = "Test Subject",
                        taskName = "Test Task",
                        totalStudyTime = 600,
                    ),
                    FeedEntry(
                        argb_color = 0xFFFFD200,
                        subJectName = "Test Subject",
                        taskName = "Test Task",
                        totalStudyTime = 20,
                    ),
                ),
                "09 May 2023" to listOf(
                    FeedEntry(
                        argb_color = 0xFFFD1200,
                        subJectName = "Test Subject",
                        taskName = "Test Task",
                    ),
                    FeedEntry(
                        argb_color = 0xFFFFD200,
                        subJectName = "Test Subject",
                        taskName = "Test Task",
                    ),
                )
            )
        ),
        continueTask = { _, _ -> run {} },
    )
}