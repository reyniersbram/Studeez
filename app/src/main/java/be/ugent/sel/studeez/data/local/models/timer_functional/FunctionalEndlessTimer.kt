package be.ugent.sel.studeez.data.local.models.timer_functional

import be.ugent.sel.studeez.screens.session.sessionScreens.EndlessSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.AbstractSessionScreen

class FunctionalEndlessTimer : FunctionalTimer(0) {

    override fun hasEnded(): Boolean {
        return false
    }

    override fun hasCurrentCountdownEnded(): Boolean {
        return false
    }

    override fun tick() {
        time.plusOne()
    }

    override fun getView(): AbstractSessionScreen {
        return EndlessSessionScreen()
    }
}