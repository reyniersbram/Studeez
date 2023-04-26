package be.ugent.sel.studeez.data.local.models.timer_functional

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

    override fun <T> accept(visitor: FunctionalTimerVisitor<T>): T {
        return visitor.visitFunctionalEndlessTimer(this)
    }
}