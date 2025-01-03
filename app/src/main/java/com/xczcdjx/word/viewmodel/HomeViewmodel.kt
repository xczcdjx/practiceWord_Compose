package com.xczcdjx.word.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import com.xczcdjx.word.constants.AnswerStatusEnum
import com.xczcdjx.word.constants.StatusEnumBtn
import com.xczcdjx.word.constants.getRandomQuestions
import com.xczcdjx.word.utils.calcFun
import kotlinx.coroutines.delay
import java.util.Locale
import java.util.Timer

class HomeViewmodel(private val ctx: Context) : ViewModel() {
    var titCount: Int by mutableIntStateOf(10)
        private set
    private var curIndex: Int by mutableIntStateOf(0)
    private var totalTopics by mutableStateOf(getRandomQuestions(titCount))
    val curTopic by derivedStateOf { totalTopics[curIndex] }
    var practiceStatus: StatusEnumBtn by mutableStateOf(StatusEnumBtn.Stop)
        private set
    var answerStatus: AnswerStatusEnum by mutableStateOf(AnswerStatusEnum.Answered)
        private set
    var rightC: Int by mutableIntStateOf(0)
        private set
    var answerC: Int by mutableIntStateOf(0)
        private set
    var selectC: String by mutableStateOf("")
        private set
    val rightRate by derivedStateOf {
        calcFun.getRate(rightC,answerC)
    }
    var expandTime= mutableLongStateOf(0L)

    fun updatePS(v: StatusEnumBtn) {
        practiceStatus = v
        if (v == StatusEnumBtn.Stop) {
            clear()
        }
    }
    fun clear() {
        practiceStatus = StatusEnumBtn.Stop
        curIndex = 0
        rightC = 0
        answerC = 0
    }

    suspend fun updateCurIndex(rightS: String, answerS: String) {
        if (practiceStatus == StatusEnumBtn.Running) {
            selectC = answerS
            answerC++
            if (rightS == answerS) {
                this.rightC++
            }
            if (curIndex < titCount - 1) {
                answerStatus = AnswerStatusEnum.Answering
                delay(500)
                answerStatus = AnswerStatusEnum.Answered
                // 索引递增
                curIndex++
                selectC = ""
            } else {
                Toast.makeText(ctx,"答完了",Toast.LENGTH_SHORT).show()
                delay(500)
                clear()
            }
        }else{
            Toast.makeText(ctx, buildAnnotatedString {
                append("请先")
                if (practiceStatus == StatusEnumBtn.Stop) append("开始") else append("继续")
                append("答题")
            }, Toast.LENGTH_SHORT).show()
        }
    }

    fun formatTime(timeInMillis: Long): String {
        val hours = timeInMillis / (1000 * 60 * 60) % 24
        val minutes = timeInMillis / (1000 * 60) % 60
        val seconds = timeInMillis / 1000 % 60
        val milliseconds = timeInMillis % 1000

        return String.format(
            Locale.getDefault(),
            "%02d:%02d:%02d.%03d",
            hours,
            minutes,
            seconds,
            milliseconds
        )
    }
}