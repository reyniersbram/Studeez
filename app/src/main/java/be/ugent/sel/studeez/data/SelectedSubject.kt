package be.ugent.sel.studeez.data

import be.ugent.sel.studeez.data.local.models.task.Subject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Used to communicate the selected subject from the subject overview to the task overview of that subject.
 * Because this is a singleton-class the view-models of both screens observe the same data.
 */
@Singleton
class SelectedSubject @Inject constructor() {
    private lateinit var subject: Subject
    operator fun invoke() = subject
    fun set(subject: Subject) {
        this.subject = subject
    }
}