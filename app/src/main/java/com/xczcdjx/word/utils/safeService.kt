package com.xczcdjx.word.utils

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
        ApiResponse(null, "Network error")
    } catch (e: Exception) {
        ApiResponse(null, "Unknown error")
    }
}