package be.ugent.sel.studeez.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds

@Composable
fun Feed(
    open: (String) -> Unit,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val feedEntries = viewModel.getFeedEntries().collectAsState(initial = emptyList())
    
    LazyColumn {
        items(feedEntries.value) {feedEntry ->
            FeedEntryCard(feedEntry = feedEntry) {
                viewModel.continueWithTask(open, feedEntry.subjectId, feedEntry.taskId)
            }
        }
    }
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