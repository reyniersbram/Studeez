package be.ugent.sel.studeez.data.local.models.timer_functional

class FunctionalPomodoroTimer(
    private var studyTime: Int,
    private var breakTime: Int, repeats: Int
): FunctionalTimer(studyTime) {

    private var breaksRemaining = repeats
    private var isInBreak = false

    override fun tick() {
        if (time.getTime() == 0 && breaksRemaining == 0){
            view = "Done!"
            return
        }

        if (time.getTime() == 0) {
            if (isInBreak) {
                breaksRemaining--
                view = "Focus! ($breaksRemaining breaks remaining)"
                time.setTime(studyTime)
            } else {
                view = "Take a break!"
                time.setTime(breakTime)
            }
            isInBreak = !isInBreak
        }
        time.minOne()
    }

    override fun hasEnded(): Boolean {
        return breaksRemaining == 0 && time.getTime() == 0
    }
}