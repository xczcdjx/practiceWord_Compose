package com.xczcdjx.word.viewmodel

import android.content.Context
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.xczcdjx.word.constants.StatusEnumBtn
import com.xczcdjx.word.constants.getRandomQuestions

class HomeViewmodel(ctx:Context) : ViewModel() {
    private var titCount: Int by mutableIntStateOf(10)
    private var curIndex: Int by mutableIntStateOf(0)
    private var totalTopics by mutableStateOf(getRandomQuestions(titCount))
    val curTopic by derivedStateOf { totalTopics[curIndex] }
    var practiceStatus: StatusEnumBtn by mutableStateOf(StatusEnumBtn.Stop)
        private set

    fun updatePS(v: StatusEnumBtn) {
        practiceStatus = v
    }

    fun updateCurIndex(i: Int) {
        if (practiceStatus==StatusEnumBtn.Stop){

        }else{
            if (curIndex < titCount - 1) {
                curIndex += i
            } else {
                practiceStatus = StatusEnumBtn.Stop
                curIndex = 0
            }
        }
    }
}