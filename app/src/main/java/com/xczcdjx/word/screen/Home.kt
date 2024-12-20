package com.xczcdjx.word.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xczcdjx.word.R
import com.xczcdjx.word.components.StatusBtn
import com.xczcdjx.word.constants.StatusEnumBtn
import com.xczcdjx.word.viewmodel.HomeViewmodel
import com.xczcdjx.word.viewmodel.VMFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun Home(
    modifier: Modifier = Modifier, vm: HomeViewmodel = viewModel(
        factory = VMFactory(
            LocalContext.current
        )
    ), goTest: () -> Unit = {}
) {
    Box {
        Image(
            painter = painterResource(R.drawable.img_practice_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier
                    .size(370.dp, 180.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
            ) {

            }
            Column(
                modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(vm.curTopic.word, fontWeight = FontWeight.Bold, fontSize = 50.sp)
                Text(vm.curTopic.sentence, color = Color.Gray)
            }
            Column(
                modifier.size(400.dp, 240.dp)
//                    .border(1.dp, Color.Gray)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                vm.curTopic.options.forEach {
                    Text(
                        it,
                        modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .height(50.dp)
                            .width(250.dp)
                            .clickable {
                                vm.updateCurIndex(1)
                            },
                        textAlign = TextAlign.Center,
                        lineHeight = 50.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatusBtn("停止测试", vm.practiceStatus) {
                    vm.updatePS(it)
                }
                StatusBtn(
                    when (vm.practiceStatus) {
                        StatusEnumBtn.Stop -> "开始测试"
                        StatusEnumBtn.Running -> "暂停测试"
                        else -> "继续测试"
                    }, vm.practiceStatus, StatusEnumBtn.Running
                ) {
                    vm.updatePS(it)
                }
            }
        }
    }
}