package be.ugent.sel.studeez.data.local.models.timer_functional

class FunctionalPomodoroTimer(
    private var studyTime: Int,
    private var breakTime: Int, repeats: Int
) : FunctionalTimer(studyTime) {

    var breaksRemaining = repeats
    var isInBreak = false

    override fun tick() {
        if (time.time == 0 && breaksRemaining == 0) {
            view = DONE
            return
        }

        if (time.time == 0) {
            if (isInBreak) {
                breaksRemaining--
                view = FOCUS_REMAINING(breaksRemaining)
                time.time = studyTime
            } else {
                view = BREAK
                time.time = breakTime
            }
            isInBreak = !isInBreak
        }
        time.minOne()
    }

    override fun hasEnded(): Boolean {
        return breaksRemaining == 0 && time.time == 0
    }
}