package com.xczcdjx.word.utils

import android.util.Log
import com.xczcdjx.word.net.ApiException
import java.io.IOException

data class ApiResponse<T>(val success: T?, val error: String? = null)
// 安全调用请求服务
suspend fun <T> safeService(apiCall: suspend () -> T): ApiResponse<T> {
    return try {
        ApiResponse(apiCall())
    } catch (e: ApiException) {
        ApiResponse(null, e.message)
    } catch (e: IOException) {
        e.message?.let { Log.e("Request Network error", it) }
        ApiResponse(null, "Network error")
    } catch (e: Exception) {
        e.message?.let { Log.e("Unknown error", it) }
        ApiResponse(null, "Unknown error")
    }
}