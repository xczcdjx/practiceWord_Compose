package com.xczcdjx.word.share

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserShareView @Inject constructor():ViewModel(){
    var token by mutableStateOf<String>("")
        private set
    fun updateToken(v:String){
        token=v
    }
    val isLogin by derivedStateOf { token.isNotEmpty() }
}