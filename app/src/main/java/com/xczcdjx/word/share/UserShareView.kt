package com.xczcdjx.word.share

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xczcdjx.word.storage.UserStoreManager
import com.xczcdjx.word.utils.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserShareView @Inject constructor(
    private val userStoreManager:UserStoreManager
):ViewModel(){
    var token by mutableStateOf<String>("")
        private set
    fun updateToken(v:String){
        token=v
        viewModelScope.launch {
            userStoreManager.setT(v)
        }
    }
    init {
        viewModelScope.launch {
            userStoreManager.getT.collect{
                    t->token=t
                LoggerUtil.info(token)
            }
        }
    }
    val isLogin by derivedStateOf { token.isNotEmpty() }
}