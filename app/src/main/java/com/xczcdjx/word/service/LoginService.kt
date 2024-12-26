package com.xczcdjx.word.service

import com.xczcdjx.word.entity.CodeEntity
import com.xczcdjx.word.entity.LoginEntity
import com.xczcdjx.word.entity.LoginFormEntity
import com.xczcdjx.word.net.Network
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @GET("user/code")
    suspend fun getCode(@Query("phone") phone:String): CodeEntity
    @POST("user/login")
    suspend fun login(@Body loginF: LoginFormEntity):LoginEntity
    companion object {
        fun instance(): LoginService = Network.createService(LoginService::class.java)
    }
}