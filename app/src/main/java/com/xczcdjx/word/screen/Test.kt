package com.xczcdjx.word.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.xczcdjx.word.share.TextShare
import com.xczcdjx.word.storage.TestStoreManager
import com.xczcdjx.word.utils.convertMillToDate
import com.xczcdjx.word.utils.convertStrToMill
import com.xczcdjx.word.utils.formatDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewmodel @Inject constructor(
    private val testInf: TextShare
) : ViewModel() {
//    private val instance = TestService.instance()
    val getFun = testInf.test()
    var testData by mutableStateOf("")
    suspend fun fetch() {
        // 此处调用safeService会自动捕获异常处理封装到success,和error参数上
        /*val res = safeService { instance.test() }
        res.success?.let {
            testData = it.name // 成功响应
        } ?: run {
            println(res.error) // 失败信息
        }*/
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Test(
    modifier: Modifier = Modifier, vm: TestViewmodel = hiltViewModel(), goBack: () -> Unit = {}
) {
    val testStore = TestStoreManager(LocalContext.current)
    val saveName = testStore.getName.collectAsState("ttt")
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
//        vm.fetch()
    }
    Scaffold(topBar = {
        TopAppBar(title = {
            Text("Test")
        })
    }) { pad ->
        Column(modifier.padding(pad)) {
//            Text(formatDate("2024-08-31 16:00:00"), fontSize = 30.sp)
            // 日期时间处理
            Text(formatDate("2024-09-02T11:44:16.266Z"), fontSize = 30.sp)
//            println(Clock.System.now())
//            println(formatNow())
//            println(formatNow(showTime = true))
            val mi=convertStrToMill("2024-08-31 16:10:00")
            println(mi)
            println(convertMillToDate(mi))
            // hilt provide
            Text(vm.getFun)
            Text(saveName.value)
            TextField(vm.testData, { v ->
                scope.launch {
                    vm.testData = v
                    testStore.setName(v)
                }
            })
            TextButton(goBack) {
                Text("Back")
            }
        }
    }
}