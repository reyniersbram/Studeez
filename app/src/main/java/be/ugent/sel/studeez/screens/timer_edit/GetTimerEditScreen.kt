package be.ugent.sel.studeez.screens.timer_edit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.ext.basicButton
import be.ugent.sel.studeez.data.local.models.timer_info.CustomTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.EndlessTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.PomodoroTimerInfo
import be.ugent.sel.studeez.data.local.models.timer_info.TimerInfoVisitor

class GetTimerEditView: TimerInfoVisitor<Unit> {

    @SuppressLint("ComposableNaming")
    override fun visitCustomTimerInfo(customTimerInfo: CustomTimerInfo) {

    }

    @SuppressLint("ComposableNaming")
    override fun visitEndlessTimerInfo(endlessTimerInfo: EndlessTimerInfo) {

    }

    @SuppressLint("ComposableNaming")
    override fun visitBreakTimerInfo(pomodoroTimerInfo: PomodoroTimerInfo) {

    }


}