package be.ugent.sel.studeez.screens.timers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.resources
import be.ugent.sel.studeez.screens.timer_overview.TimerEntry
import be.ugent.sel.studeez.screens.timer_overview.TimerOverviewViewModel

@Composable
fun TimerSelectScreen(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: TimerSelectViewModel = hiltViewModel()
) {

    val timers = viewModel.getAllTimers().collectAsState(initial = emptyList())

    PrimaryScreenTemplate(
        title = resources().getString(R.string.timers),
        open = open,
        openAndPopUp = openAndPopUp
    ) {

        Column {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {

                // All timers
                items(timers.value) {
                    TimerEntry(timerInfo = it, true) { timerInfo ->
                        viewModel.startSession()
                    }
                }
            }
        }

    }
}