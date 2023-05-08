package be.ugent.sel.studeez.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.common.composable.DateText
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds

@Composable
fun Feed(
    open: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val feedEntries = viewModel.getFeedEntries().collectAsState(initial = emptyMap())

    LazyColumn {

        items(feedEntries.value.toList()) { (date, feedEntries) ->
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
                    viewModel.continueWithTask(open, feedEntry.subjectId, feedEntry.taskId)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun FeedPreview() {
    Feed(
        open = {},
        viewModel = hiltViewModel(),
    )
}