package com.xczcdjx.word.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.xczcdjx.word.R
import com.xczcdjx.word.entity.LoginFormEntity
import com.xczcdjx.word.net.Network
import com.xczcdjx.word.service.LoginService
import com.xczcdjx.word.share.UserShareView
import com.xczcdjx.word.utils.safeService
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    userVm: UserShareView = hiltViewModel(),
    back: () -> Unit = {}
) {
    // 获取屏幕宽、高度
    /*var screenWidth: Float
    var screenHeight: Float
    with(LocalDensity.current) {
        screenWidth = LocalConfiguration.current.screenWidthDp.dp.toPx()
        screenHeight = LocalConfiguration.current.screenHeightDp.dp.toPx()
    }*/
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }

    val scope= rememberCoroutineScope()
    val service = LoginService.instance()

    BoxWithConstraints(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(R.drawable.bg),
            null,
            modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // 宽高撑满
        )
        // 右上至左下
        Box(
            modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xffbb8378), Color.Transparent),
                        start = Offset(constraints.maxWidth.toFloat(), 0f),
                        end = Offset(0f, constraints.maxHeight.toFloat())
                    )
                )
        )
        // 左下至右上
        Box(
            modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xff149ee7), Color.Transparent),
                        start = Offset(0f, constraints.maxHeight.toFloat()),
                        end = Offset(constraints.maxWidth.toFloat(), 0f)
                    )
                )
        )
        Column(
            modifier
                .fillMaxWidth()
                .height(480.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("用户登录", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 35.sp)
                Spacer(modifier.height(10.dp))
                Text("富强、明主、文明、和谐", color = Color.White)
            }
            Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(username, {
                    username = it
                }, singleLine = true, leadingIcon = {
                    Icon(Icons.Default.Person, null, tint = Color.White)
                }, label = {
                    Text("用户名", fontSize = 14.sp, color = Color.White)
                },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent, // 设置获得焦点时背景透明
                        unfocusedContainerColor = Color.Transparent, // 设置失去焦点时背景透明
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedLabelColor = Color.LightGray,
                        unfocusedLabelColor = Color.LightGray
                    )
                )
                TextField(password, {
                    password = it
                }, singleLine = true, leadingIcon = {
                    Icon(Icons.Default.Password, null, tint = Color.White)
                }, label = {
                    Text("密码", fontSize = 14.sp, color = Color.White)
                },
                    trailingIcon = {
                        IconButton({
                            showPass = !showPass
                        }) {
                            Icon(
                                if (showPass) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                null,
                                tint = Color.White
                            )
                        }
                    },
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent, // 设置获得焦点时背景透明
                        unfocusedContainerColor = Color.Transparent, // 设置失去焦点时背景透明
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedLabelColor = Color.LightGray,
                        unfocusedLabelColor = Color.LightGray
                    )
                )
                Spacer(modifier.height(8.dp))
                TextButton({
                    scope.launch {
                        val res=safeService { service.getCode(username) }
                            res.success?.let {
                                password=it.data
                            }?:run {
                                println(res.error)
                        }
                    }
                }) { Text("获取验证码", color = Color.White) }
                Spacer(modifier.height(8.dp))
                TextButton({
                    scope.launch {
                        val res=safeService { service.login(LoginFormEntity(username,password))  }
                        res.success?.let {
                            userVm.updateToken(it.data)
                            back()
                        }?:run {
                            println(res.error)
                        }
                    }
                }) { Text("登录", color = Color.White) }
            }
            TextButton({

            }) {
                Text("还没有账号？点击立即注册", color = Color.White, fontSize = 14.sp)
            }
        }
    }
}