package be.ugent.sel.studeez.data.local.models.timer_functional

class FunctionalPomodoroTimer(
    private var studyTime: Int,
    private var breakTime: Int, repeats: Int
): FunctionalTimer(studyTime) {

    private var breaksRemaining = repeats
    private var isInBreak = false

    override fun tick() {
        if (time.time == 0 && breaksRemaining == 0){
            view = "Done!"
            return
        }

        if (time.time == 0) {
            if (isInBreak) {
                breaksRemaining--
                view = "Focus! ($breaksRemaining breaks remaining)"
                time.time = studyTime
            } else {
                view = "Take a break!"
                time.time =breakTime
            }
            isInBreak = !isInBreak
        }
        time.minOne()
    }

    override fun hasEnded(): Boolean {
        return breaksRemaining == 0 && time.time == 0
    }
}