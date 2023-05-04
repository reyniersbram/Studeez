package be.ugent.sel.studeez.data.local.models.timer_functional

class FunctionalPomodoroTimer(
    private var studyTime: Int,
    private var breakTime: Int, repeats: Int
) : FunctionalTimer(studyTime) {

    var breaksRemaining = repeats
    var isInBreak = false

    override fun tick() {
        if (hasEnded()) {
            return
        }

        if (hasCurrentCountdownEnded()) {
            if (isInBreak) {
                breaksRemaining--
                time.time = studyTime
            } else {
                time.time = breakTime
            }
            isInBreak = !isInBreak
        }
        time--

        if (!isInBreak) {
            totalStudyTime++
        }
    }

    override fun hasEnded(): Boolean {
        return !hasBreaksRemaining() && hasCurrentCountdownEnded()
    }

    private fun hasBreaksRemaining(): Boolean {
        return breaksRemaining > 0
    }

    override fun hasCurrentCountdownEnded(): Boolean {
        return time.time == 0
    }

    override fun <T> accept(visitor: FunctionalTimerVisitor<T>): T {
        return visitor.visitFunctionalBreakTimer(this)
    }
}