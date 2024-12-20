package com.xczcdjx.word.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xczcdjx.word.constants.StatusEnumBtn

@Composable
fun StatusBtn(
    tit: String = "按钮",
    s: StatusEnumBtn,
    os: StatusEnumBtn = StatusEnumBtn.Stop,
    click: (v: StatusEnumBtn) -> Unit
) {
    val m = Modifier
    val fc = when (s) {
        StatusEnumBtn.Stop -> Color.White
        StatusEnumBtn.Running -> Color.Green
        else -> Color.Red
    }
    when (os) {
        StatusEnumBtn.Stop -> TextButton(
            {
                click(StatusEnumBtn.Stop)
            }, modifier = m
                .size(100.dp, 50.dp)
                .border(
                    1.dp, if (s == StatusEnumBtn.Stop) Color.Gray else Color.Black, CircleShape
                )
                .clip(CircleShape)
                .background(Color.Transparent), enabled = s != StatusEnumBtn.Stop
        ) {
            Text(
                tit,
                color = if (s == StatusEnumBtn.Stop) Color.Gray else Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        else -> TextButton(
            {
                click(if (s == StatusEnumBtn.Running) StatusEnumBtn.Paused else StatusEnumBtn.Running)
            }, modifier = m
                .size(100.dp, 50.dp)
                .clip(CircleShape)
                .background(Color.Black)
        ) {
            Text(tit, color = fc, fontWeight = FontWeight.Bold)
        }
    }
}