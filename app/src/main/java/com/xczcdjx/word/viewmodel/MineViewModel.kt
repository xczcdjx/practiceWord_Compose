package com.xczcdjx.word.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.xczcdjx.word.components.MItem
import com.xczcdjx.word.entity.UserEntity
import com.xczcdjx.word.share.UserShareView
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MineViewModel @Inject constructor(val user:UserShareView):ViewModel(){
    val isLogin by derivedStateOf { user.isLogin }
    val userInfo by mutableStateOf<UserEntity>(UserEntity("djx","https://limmerence.store/static/media/2024/09/12/mobx-1726108688108.png"))
    val mineList:List<MItem> = listOf(
        MItem("record","打卡记录", Icons.AutoMirrored.Outlined.ReceiptLong),
        MItem("update","检查更新", Icons.Default.Update),
        MItem("about","关于", Icons.Outlined.Info),
    )
}