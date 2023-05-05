package be.ugent.sel.studeez.data

import be.ugent.sel.studeez.data.local.models.task.Task
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Used to communicate the selected task from the task overview other screens.
 * Because this is a singleton-class the view-models of both screens observe the same data.
 */
@Singleton
class SelectedTask @Inject constructor() {
    private lateinit var task: Task

    operator fun invoke() = task
    fun set(task: Task) {
        this.task = task
    }

    fun isSet() = this::task.isInitialized
}