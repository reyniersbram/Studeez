package be.ugent.sel.studeez.common.composable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import be.ugent.sel.studeez.data.local.models.task.Subject

@Composable
fun SubjectEntry(
    subject: Subject,
) {
    Text(text = subject.name)
}

@Preview
@Composable
fun SubjectEntryPreview() {
    SubjectEntry(subject = Subject(name = "Test Subject"))
}