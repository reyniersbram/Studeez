package be.ugent.sel.studeez.common.composable.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R.string as AppText
import be.ugent.sel.studeez.common.composable.StealthButton
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.timer_functional.HoursMinutesSeconds

@Composable
fun SubjectEntry(
    subject: Subject,
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
                        .background(Color(subject.argb_color)),
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        text = subject.name,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${HoursMinutesSeconds(subject.time)}",
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = stringResource(id = AppText.tasks)
                            )
                            Text(text = "0/0") // TODO
                        }
                    }
                }
            }
            StealthButton(
                text = AppText.view_tasks,
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .weight(1f)
            ) {
                onViewSubject()
            }
        }
    }
}

@Preview
@Composable
fun SubjectEntryPreview() {
    SubjectEntry(
        subject = Subject(
            name = "Test Subject",
            argb_color = 0xFFFFD200,
            time = 60
        ),
    ) {}
}

@Preview
@Composable
fun OverflowSubjectEntryPreview() {
    SubjectEntry(
        subject = Subject(
            name = "Testttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt",
            argb_color = 0xFFFFD200,
            time = 60
        ),
    ) {}
}