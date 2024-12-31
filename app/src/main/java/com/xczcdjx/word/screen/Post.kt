package com.xczcdjx.word.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Countertops
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.xczcdjx.word.R
import com.xczcdjx.word.components.ShowTest
import com.xczcdjx.word.utils.calcFun
import com.xczcdjx.word.viewmodel.PostViewModel

@Composable
fun PostPage(
    pad: PaddingValues,
    modifier: Modifier = Modifier,
    vm: PostViewModel = hiltViewModel(),
    goLogin: () -> Unit = {}
) {
    val postListUiState by vm.uiState.collectAsState()
    LaunchedEffect(Unit) {
        vm.fetData()
    }
    Column(
        modifier = Modifier
            .padding(top = pad.calculateTopPadding(), bottom = pad.calculateBottomPadding())
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            "英语打卡圈",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        LazyColumn {
           /* item {

            }*/
            items(postListUiState, key = { it.id }) { p ->
                Card(
                    modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(300.dp)
                    /*.shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(4.dp),
                        clip = false, // 如果需要裁剪内容设置为 true
                        ambientColor = Color.Black, // 设置阴影的主色
                        spotColor = Color.Black // 设置阴影的次色
                    )*/,
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Column(modifier.padding(15.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                        Row(modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val painter =rememberAsyncImagePainter(p.avatarURL)
                                Image(
                                    painter = painter,
                                    null,
                                    modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier.width(10.dp))
                                Text(p.nickname)
                            }
                            Text(p.createTime, color = Color.Gray)
                        }
                        Text(p.postText, fontSize = 18.sp)
                        Row(modifier.height(145.dp).fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                            Surface(modifier.weight(1f)/*.border(1.dp,Color.Red,
                                RoundedCornerShape(10.dp)
                            )*/.fillMaxHeight(), color = Color.Transparent,shape = RoundedCornerShape(10.dp),) {
                                Box {
                                    Image(painter = painterResource(R.drawable.img_post_bg),null, modifier = modifier.fillParentMaxSize())
                                    Column(modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.SpaceBetween) {
                                        val c=Color.White
                                        ShowTest("用时", Icons.Outlined.Timer, tintC = c) {
                                            Text(
                                                "${p.timeUsed}秒",
                                                color = c
                                            )
                                        }
                                        ShowTest("正确率", Icons.Outlined.CheckCircle,tintC = c) {
                                            val cr=calcFun.getRate(p.rightCount.toInt(),p.answeredCount.toInt())
                                            Text(
                                                stringResource(R.string.word_right, cr),
                                                color = c
                                            )
                                        }
                                        ShowTest("作答个数", Icons.Outlined.Countertops,tintC = c) {
                                            Text(
                                                p.answeredCount.toString(),
                                                color = c
                                            )
                                        }
                                    }
                                }
                            }
                            Column(modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                val c=if(p.isLike==1)Color(0xFF84DCC1) else Color.Gray
                                Text(p.likeCount.toString(), color = c)
                                Icon(Icons.Default.Favorite,null, tint = c, modifier = modifier.size(25.dp).clickable {

                                })
                            }
                        }
                    }
                }
            }
        }
    }
}