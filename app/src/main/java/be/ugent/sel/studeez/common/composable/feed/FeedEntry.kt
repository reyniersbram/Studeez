package be.ugent.sel.studeez.common.composable.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.data.local.models.FeedEntry
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds
import be.ugent.sel.studeez.R.string as AppText

@Composable
fun FeedEntry(
    feedEntry: FeedEntry,
    continueWithTask: () -> Unit,
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
                    .weight(11f)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(feedEntry.argb_color)),
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                        modifier = Modifier.weight(13f)
                    ) {
                        Text(
                            text = feedEntry.subJectName,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                        Text(
                            text = feedEntry.taskName,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                        )
                    }
                    Text(
                        text = HoursMinutesSeconds(feedEntry.totalStudyTime).toString(),
                        modifier = Modifier.weight(8f),
                    )
                }
            }
            val buttonText: Int =
                if (feedEntry.isArchived) AppText.deleted else AppText.continue_task
            StealthButton(
                text = buttonText,
                enabled = !feedEntry.isArchived,
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .weight(6f)
            ) {
                if (!feedEntry.isArchived) {
                    continueWithTask()
                }
            }

        }
    }
}

@Preview
@Composable
fun FeedEntryPreview() {
    FeedEntry(
        continueWithTask = {},
        feedEntry = FeedEntry(
            argb_color = 0xFFFFD200,
            subJectName = "Test Subject",
            taskName = "Test Task",
            totalStudyTime = 20,
        )
    )
}

@Preview
@Composable
fun FeedEntryOverflowPreview() {
    FeedEntry(
        continueWithTask = {},
        feedEntry = FeedEntry(
            argb_color = 0xFFFFD200,
            subJectName = "Test Subject",
            taskName = "Test Taskkkkkkkkkkkkkkkkkkkkkkkkk",
            totalStudyTime = 20,
        )
    )
}