package be.ugent.sel.studeez.data.local.models.timer_functional

import be.ugent.sel.studeez.screens.session.sessionScreens.CustomSessionScreen
import be.ugent.sel.studeez.screens.session.sessionScreens.AbstractSessionScreen

class FunctionalCustomTimer(studyTime: Int) : FunctionalTimer(studyTime) {

    override fun tick() {
        if (!hasEnded()) {
            time.minOne()
        }
    }

    override fun hasEnded(): Boolean {
        return time.time == 0
    }

    override fun hasCurrentCountdownEnded(): Boolean {
        return hasEnded()
    }

    override fun <T> accept(visitor: FunctionalTimerVisitor<T>): T {
        return visitor.visitFunctionalCustomTimer(this)
    }

}