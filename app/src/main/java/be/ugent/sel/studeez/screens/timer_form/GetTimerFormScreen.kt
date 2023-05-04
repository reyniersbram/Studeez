package be.ugent.sel.studeez.screens.timer_form.timer_edit

import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.EndlessTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.PomodoroTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfoVisitor
import be.ugent.sel.studeez.screens.timer_form.form_screens.AbstractTimerEditScreen
import be.ugent.sel.studeez.screens.timer_form.form_screens.BreakTimerEditScreen
import be.ugent.sel.studeez.screens.timer_form.form_screens.CustomTimerEditScreen
import be.ugent.sel.studeez.screens.timer_form.form_screens.EndlessTimerEditScreen

class GetTimerEditScreen: TimerInfoVisitor<AbstractTimerEditScreen> {

    override fun visitCustomTimerInfo(customTimerInfo: CustomTimerInfo): AbstractTimerEditScreen {
        return CustomTimerEditScreen(customTimerInfo)
    }

    override fun visitEndlessTimerInfo(endlessTimerInfo: EndlessTimerInfo): AbstractTimerEditScreen {
        return EndlessTimerEditScreen(endlessTimerInfo)
    }

    override fun visitBreakTimerInfo(pomodoroTimerInfo: PomodoroTimerInfo): AbstractTimerEditScreen {
        return BreakTimerEditScreen(pomodoroTimerInfo)
    }


}