package com.xczcdjx.word.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xczcdjx.word.constants.AnswerEnum
import com.xczcdjx.word.constants.AnswerStatusEnum

@Composable
fun AnswerText(
    tit: String,
    rightT: String,
    selectT: String,
    ansS: AnswerStatusEnum,
    modifier: Modifier = Modifier,
    click: () -> Unit
) {
    /*var curAnswer by remember { mutableStateOf(AnswerEnum.Default) }
    LaunchedEffect(ansS) {
        if (ansS==AnswerStatusEnum.Answering){
            if (tit == rightT) {
                curAnswer = AnswerEnum.Right
            } else {
                if (selectT == tit) {
                    curAnswer = AnswerEnum.Error
                }
            }
        }else{
            curAnswer = AnswerEnum.Default
        }
    }*/
    // 优化监测重组值
    val curAnswer = remember(ansS) {
        when (ansS) {
            AnswerStatusEnum.Answering -> {
                when (tit) {
                    rightT -> AnswerEnum.Right
                    selectT -> AnswerEnum.Error
                    else -> AnswerEnum.Default
                }
            }
            else -> AnswerEnum.Default
        }
    }
    Row(
        modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                when (curAnswer) {
                    AnswerEnum.Error -> Color.Red
                    AnswerEnum.Right -> Color.Green
                    else -> Color.White
                }
            )
            .height(50.dp)
            .width(250.dp)
            .clickable {
                click()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (curAnswer == AnswerEnum.Right) Icon(
            Icons.Outlined.Check,
            null,
            modifier.offset(x = (-20).dp)
        )
        else if (curAnswer == AnswerEnum.Error) Icon(
            Icons.Default.Delete,
            null,
            modifier.offset(x = (-20).dp)
        )
        Text(
            tit,
            textAlign = TextAlign.Center,
            lineHeight = 50.sp,
            fontWeight = FontWeight.Bold
        )
    }
}