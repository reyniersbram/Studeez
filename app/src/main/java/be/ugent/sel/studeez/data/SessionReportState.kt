package be.ugent.sel.studeez.data

import be.ugent.sel.studeez.data.local.models.SessionReport
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Used to communicate the SelectedTimer from the selection screen to the session screen.
 * Because this is a singleton-class the view-models of both screens observe the same data.
 */
@Singleton
class SessionReportState @Inject constructor(){
    var sessionReport: SessionReport? = null
}