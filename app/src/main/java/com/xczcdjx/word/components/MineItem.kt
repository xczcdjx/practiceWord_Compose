package com.xczcdjx.word.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MItem(val key: String, val title: String, val icon: ImageVector)

@Composable
fun MineItem(m: MItem, modifier: Modifier = Modifier, click: (k: String) -> Unit) {
    Row(
        modifier
            .fillMaxWidth()
            .clickable(
//            indication = null, // 移除clickable效果
                indication = rememberRipple(color = Color.Gray), // 自定义 Ripple 颜色
                interactionSource = remember { MutableInteractionSource() } // 控制交互状态
            ) { click(m.key) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(m.icon, null)
            Text(
                m.title,
                modifier.padding(start = 10.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Icon(Icons.AutoMirrored.Outlined.KeyboardArrowRight, null)
    }
    HorizontalDivider(modifier.padding(vertical = 15.dp))
}