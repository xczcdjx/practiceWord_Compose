package com.xczcdjx.word.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(modifier: Modifier = Modifier, goTest: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Home")
        })
    }) { pad ->
        Column(modifier.padding(pad)) {
            TextButton(goTest) {
                Text("Go Test")
            }
        }
    }
}