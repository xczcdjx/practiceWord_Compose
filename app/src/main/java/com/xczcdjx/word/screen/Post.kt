package com.xczcdjx.word.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PostPage(pad: PaddingValues, modifier: Modifier = Modifier, goLogin: () -> Unit = {}) {
    Column(modifier = Modifier.padding(top = pad.calculateTopPadding())) {
        TextButton(goLogin) {
            Text("go Test")
        }
    }
}