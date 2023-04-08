package be.ugent.sel.studeez.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.ugent.sel.studeez.common.snackbar.SnackbarManager
import be.ugent.sel.studeez.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import be.ugent.sel.studeez.domain.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class StudeezViewModel(private val logService: LogService) : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}