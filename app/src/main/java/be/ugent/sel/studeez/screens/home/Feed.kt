package be.ugent.sel.studeez.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.common.ext.spacer
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds

@Composable
fun Feed(
    open: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val feedEntries = viewModel.getFeedEntries().collectAsState(initial = emptyMap())
    
    LazyColumn {

        items(feedEntries.value.toList()) {(date, feedEntries) ->
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
                FeedEntryCard(feedEntry = feedEntry) {
                    viewModel.continueWithTask(open, feedEntry.subjectId, feedEntry.taskId)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun DateText(date: String) {
    Text(
        text = date,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(horizontal = 10.dp)
    )
}

@Composable
fun FeedEntryCard(
    feedEntry: FeedEntry,
    onViewSubject: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(feedEntry.argb_color)),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        text = feedEntry.subJectName,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = feedEntry.taskName)
                        Text(text = HoursMinutesSeconds(feedEntry.totalStudyTime).toString())
                    }
                }
            }
            StealthButton(
                text = R.string.start,
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .weight(1f)
            ) {
                onViewSubject()
            }
        }
    }
}