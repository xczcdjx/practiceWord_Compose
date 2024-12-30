package com.xczcdjx.word.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.xczcdjx.word.entity.GridEntity
import com.xczcdjx.word.entity.ListEntity
import com.xczcdjx.word.entity.RecordGrid
import com.xczcdjx.word.entity.RecordList
import com.xczcdjx.word.entity.ShowType
import com.xczcdjx.word.service.PostService
import com.xczcdjx.word.utils.safeService
import kotlinx.coroutines.launch

@Composable
fun PostPage(pad: PaddingValues, modifier: Modifier = Modifier, goLogin: () -> Unit = {}) {
    val service = PostService.instance()
    var text by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf<ShowType?>(null) }
    var isExposedDropdownOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    suspend fun fetchD() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // Kotlin 支持
            .build()
        val adapter = moshi.adapter(GridEntity::class.java)
        val adapter2 = moshi.adapter(ListEntity::class.java)
        val res = safeService {
            service.search(type = selectedOption?.t).string()
        }
        res.success?.let {
            when(selectedOption){
                ShowType.listType->{
                    val t=adapter2.fromJson(it)
                    text="$t"
                }
                else->{
                    val t=adapter.fromJson(it)
                    text="$t"
                }
            }
        } ?: run {
            println(res.error)
        }
    }
    LaunchedEffect(selectedOption) {
        fetchD()
    }
    Column(modifier = Modifier.padding(top = pad.calculateTopPadding())) {
        TextButton(goLogin) {
            Text("go Test")
        }
        Row {
            Column() {
                DropdownMenu(
                    expanded = isExposedDropdownOpen,
                    onDismissRequest = { isExposedDropdownOpen = false }
                ) {
                    DropdownMenuItem(onClick = {
                        selectedOption = null
                        isExposedDropdownOpen = false
                    }, text = {
                        Text("-")
                    })
                    DropdownMenuItem(onClick = {
                        selectedOption = ShowType.gridType
                        isExposedDropdownOpen = false
                    }, text = {
                        Text("grid")
                    })
                    DropdownMenuItem(onClick = {
                        selectedOption = ShowType.listType
                        isExposedDropdownOpen = false
                    }, text = {
                        Text("list")
                    })
                }

                Button(onClick = {
                    isExposedDropdownOpen = true
                }) {
                    Text(text = "type:${selectedOption ?: '-'}")
                }
            }
            TextButton({
                scope.launch {

                }
            }) {
                Text("get open Data")
            }
        }
        LazyColumn(modifier.fillMaxSize()) {
            item {
                Text(text)
            }
        }
    }
}