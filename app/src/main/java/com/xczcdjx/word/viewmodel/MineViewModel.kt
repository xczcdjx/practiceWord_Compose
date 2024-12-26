package com.xczcdjx.word.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.Info
import androidx.lifecycle.ViewModel
import com.xczcdjx.word.components.MItem

class MineViewModel:ViewModel(){
    val mineList:List<MItem> = listOf(
        MItem("record","打卡记录", Icons.AutoMirrored.Outlined.ReceiptLong),
        MItem("update","检查更新", Icons.Default.Update),
        MItem("about","关于", Icons.Outlined.Info),
    )
}