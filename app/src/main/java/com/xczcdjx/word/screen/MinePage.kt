package com.xczcdjx.word.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xczcdjx.word.R
import com.xczcdjx.word.components.MItem
import com.xczcdjx.word.components.MineItem
import com.xczcdjx.word.router.Routes
import com.xczcdjx.word.viewmodel.MineViewModel

@Composable
@Preview
fun MinePage(
//    pad: PaddingValues,
    modifier: Modifier = Modifier, vm:MineViewModel= hiltViewModel(), goNext: (v:String) -> Unit = {}) {
    Box(modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.img_avatar),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(400.dp).blur(16.dp)
        )
        Column {
            Column(modifier.height(300.dp).fillMaxWidth(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.img_avatar),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape).size(80.dp)
                        .clickable {
                            if(vm.isLogin){
                                goNext(Routes.Test.route)
                            }
                            else goNext(Routes.Login.route)
                        }
                )
                Text(if(!vm.isLogin)"请登录" else "xczcdjx",modifier.padding(vertical = 6.dp))
                if(!vm.isLogin) Text("请点击上方头像登录", fontSize = 12.sp)
            }
            Card(modifier.padding(top = 15.dp,).fillMaxSize().clip(
                RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ), colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier.fillMaxSize().padding(20.dp)) {
                    vm.mineList.forEachIndexed { index, mItem ->
                        MineItem(mItem) {key->
                            println(key)
                        }
                    }
                    if(vm.isLogin){
                        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Button({
                                vm.user.updateToken("")
                            }) {
                                Text("退出登录")
                            }
                        }
                    }
                }
            }
        }
    }
}