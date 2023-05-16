package be.ugent.sel.studeez.screens.timer_form

import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.EndlessTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.PomodoroTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfoVisitor
import be.ugent.sel.studeez.screens.timer_form.form_screens.AbstractTimerFormScreen
import be.ugent.sel.studeez.screens.timer_form.form_screens.BreakTimerFormScreen
import be.ugent.sel.studeez.screens.timer_form.form_screens.CustomTimerFormScreen
import be.ugent.sel.studeez.screens.timer_form.form_screens.EndlessTimerFormScreen

class GetTimerFormScreen : TimerInfoVisitor<AbstractTimerFormScreen> {

    override fun visitCustomTimerInfo(customTimerInfo: CustomTimerInfo): AbstractTimerFormScreen {
        return CustomTimerFormScreen(customTimerInfo)
    }

    override fun visitEndlessTimerInfo(endlessTimerInfo: EndlessTimerInfo): AbstractTimerFormScreen {
        return EndlessTimerFormScreen(endlessTimerInfo)
    }

    override fun visitBreakTimerInfo(pomodoroTimerInfo: PomodoroTimerInfo): AbstractTimerFormScreen {
        return BreakTimerFormScreen(pomodoroTimerInfo)
    }


}