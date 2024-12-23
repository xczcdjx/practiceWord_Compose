package com.xczcdjx.word.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xczcdjx.word.R
import com.xczcdjx.word.components.AnswerText
import com.xczcdjx.word.components.ShowTest
import com.xczcdjx.word.components.StatusBtn
import com.xczcdjx.word.constants.StatusEnumBtn
import com.xczcdjx.word.viewmodel.HomeViewmodel
import com.xczcdjx.word.viewmodel.VMFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    pad:PaddingValues,
    modifier: Modifier = Modifier, vm: HomeViewmodel = viewModel(
        factory = VMFactory(
            LocalContext.current
        )
    ), goLogin: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    // 计时器处理
    DisposableEffect(vm.practiceStatus) {
        val timer=Timer()
        var f:Boolean=false
        when(vm.practiceStatus){
            StatusEnumBtn.Stop-> {
                timer.cancel()
                vm.expandTime.longValue=0
            }
            StatusEnumBtn.Running->{
                f=false
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        if (f) {
                            println("Task is paused") // 实际逻辑
                        } else {
                            vm.expandTime.longValue+=1
                        }
                    }
                },0,1)
            }
            else ->{
                f=true
            }
        }
        onDispose {
            timer.cancel()
        }
    }
    Box(modifier = modifier.padding(bottom = pad.calculateBottomPadding())) {
        Image(
            painter = painterResource(R.drawable.img_practice_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier
                .fillMaxSize()
                .padding(top=pad.calculateTopPadding(),),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier
                    .padding(horizontal = 15.dp)
                    .size(370.dp, 180.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 20.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ShowTest("正确率", Icons.Outlined.CheckCircle) {
                    Text(
                        stringResource(R.string.word_right, vm.rightRate),
                        modifier.width(120.dp),
                        textAlign = TextAlign.Center
                    )
                }
                ShowTest("进度", Icons.Default.Refresh) {
                    LinearProgressIndicator({
                        (vm.answerC / vm.titCount.toFloat())
                    },modifier.width(120.dp))
                }
                ShowTest("个数", Icons.Default.Add) {
                    Text(
                        vm.titCount.toString(),
                        modifier
                            .size(120.dp, 25.dp)
                            .clip(CircleShape)
                            .background(Color(0x8f666666)),
                        lineHeight = 25.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                ShowTest("用时", Icons.Outlined.DateRange) {
                    Text(vm.formatTime(vm.expandTime.longValue), modifier.width(120.dp), textAlign = TextAlign.Center)
                }
            }
            Column(
                modifier.padding(horizontal = 20.dp).height(100.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(vm.curTopic.word, fontWeight = FontWeight.Bold, fontSize = 50.sp)
                Text(vm.curTopic.sentence, color = Color.Gray, textAlign = TextAlign.Center)
            }
            Column(
                modifier.size(400.dp, 240.dp)
//                    .border(1.dp, Color.Gray)
                ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                vm.curTopic.options.forEach {
                    AnswerText(
                        tit = it,
                        ansS = vm.answerStatus,
                        rightT = vm.curTopic.answer,
                        selectT = vm.selectC
                    ) {
                        scope.launch {
                            vm.updateCurIndex(vm.curTopic.answer, it)
                        }
                    }
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