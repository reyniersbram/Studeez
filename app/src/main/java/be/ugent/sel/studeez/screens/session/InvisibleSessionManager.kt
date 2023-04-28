package be.ugent.sel.studeez.screens.session

import kotlinx.coroutines.delay
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
object InvisibleSessionManager {
    private var viewModel: SessionViewModel? = null

    fun setParameters(viewModel: SessionViewModel) {
        this.viewModel = viewModel
    }

    suspend fun updateTimer() {
        if (viewModel != null) {
            while (true) {
                delay(1.seconds)
                viewModel!!.getTimer().tick()
            }
        }
    }
}