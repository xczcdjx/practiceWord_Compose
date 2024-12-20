package com.xczcdjx.word.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import com.xczcdjx.word.constants.StatusEnumBtn
import com.xczcdjx.word.constants.getRandomQuestions

class HomeViewmodel(private val ctx: Context) : ViewModel() {
    var titCount: Int by mutableIntStateOf(10)
        private set
    private var curIndex: Int by mutableIntStateOf(0)
    private var totalTopics by mutableStateOf(getRandomQuestions(titCount))
    val curTopic by derivedStateOf { totalTopics[curIndex] }
    var practiceStatus: StatusEnumBtn by mutableStateOf(StatusEnumBtn.Stop)
        private set
    var rightC: Int by mutableIntStateOf(0)
        private set
    var answerC: Int by mutableIntStateOf(0)
        private set
    val rightRate by derivedStateOf {
        if (answerC == 0) "0" else String.format("%.2f", rightC.toFloat() / answerC * 100)
    }
    fun updatePS(v: StatusEnumBtn) {
        practiceStatus = v
    }

    fun updateCurIndex(i: Int) {
        if (practiceStatus == StatusEnumBtn.Running) {
            if (curIndex < titCount - 1) {
                curIndex += i
            } else {
                practiceStatus = StatusEnumBtn.Stop
                curIndex = 0
            }
        }else{
            Toast.makeText(ctx, buildAnnotatedString {
                append("请先")
                if (practiceStatus == StatusEnumBtn.Stop) append("开始") else append("继续")
                append("答题")
            }, Toast.LENGTH_SHORT).show()
        }
    }
}