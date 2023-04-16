package be.ugent.sel.studeez.data.local.models.timer_functional

class FunctionalCustomTimer(studyTime: Int): FunctionalTimer(studyTime) {

    override fun tick() {
        if (time.getTime() == 0) {
            view = "Done!"
        } else {
            time.minOne()
        }
    }

    override fun hasEnded(): Boolean {
        return time.getTime() == 0
    }
}