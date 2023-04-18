package be.ugent.sel.studeez.data.local.models.timer_functional

class FunctionalCustomTimer(studyTime: Int) : FunctionalTimer(studyTime) {

    override fun tick() {
        if (time.time == 0) {
            view = StudyState.DONE
        } else {
            time.minOne()
        }
    }

    override fun hasEnded(): Boolean {
        return time.time == 0
    }
}