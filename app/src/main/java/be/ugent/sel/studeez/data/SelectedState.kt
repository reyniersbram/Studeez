package be.ugent.sel.studeez.data

import be.ugent.sel.studeez.data.local.models.SessionReport
import be.ugent.sel.studeez.data.local.models.task.Subject
import be.ugent.sel.studeez.data.local.models.task.Task
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Used to cummunicate between viewmodels.
 */
abstract class SelectedState<T> {
    abstract var value: T
    operator fun invoke() = value
    fun set(newValue: T) {
        this.value = newValue
    }
}

@Singleton
class SelectedSessionReport @Inject constructor() : SelectedState<SessionReport>() {
    override lateinit var value: SessionReport
}

@Singleton
class SelectedTask @Inject constructor() : SelectedState<Task>() {
    override lateinit var value: Task
}

@Singleton
class SelectedTimer @Inject constructor() : SelectedState<FunctionalTimer>() {
    override lateinit var value: FunctionalTimer
}

@Singleton
class SelectedSubject @Inject constructor() : SelectedState<Subject>() {
    override lateinit var value: Subject
}

@Singleton
class SelectedTimerInfo @Inject constructor() : SelectedState<TimerInfo>() {
    override lateinit var value: TimerInfo
}