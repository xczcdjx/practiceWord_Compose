package com.xczcdjx.word.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xczcdjx.word.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun SplashScreen(modifier: Modifier = Modifier, goHome: () -> Unit = {}) {
    var startAnimation by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    // 控制 Image 和 Text 的偏移量
    val imageOffsetX by animateFloatAsState(
        label = "startAnimate", targetValue = if (startAnimation) 0f else -400f, // 从左侧开始
        animationSpec = tween(durationMillis = 2000),
        finishedListener = {
            scope.launch {
                delay(500)
                goHome()
            }
        }
    )
    val textOffsetX by animateFloatAsState(
        label = "startAnimate2", targetValue = if (startAnimation) 0f else 400f, // 从右侧开始
        animationSpec = tween(durationMillis = 2000)
    )

    // 开始动画
    LaunchedEffect(Unit) {
        startAnimation = true
    }
    Box {
        Image(
            painter = painterResource(R.drawable.img_splash_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop, // 调整图像以填充容器
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier
                .align(Alignment.TopCenter)
                .padding(top = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(R.drawable.ic_logo),
                contentDescription = null,
                contentScale = ContentScale.Crop, // 调整图像以填充容器
                modifier = Modifier
                    .size(80.dp)
                    .offset {
                        IntOffset(imageOffsetX.toInt(), 0)
                    })
            Text("快速记忆单词",
                color = Color.White,
                fontSize = 16.sp,
                modifier = modifier
                    .padding(top = 10.dp)
                    .offset {
                        IntOffset(textOffsetX.toInt(), 0)
                    })
        }
        Text(
            "power by xczcdjx",
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter)
        )
    }
}