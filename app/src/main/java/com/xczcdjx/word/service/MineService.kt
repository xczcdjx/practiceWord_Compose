package com.xczcdjx.word.service

import com.xczcdjx.word.entity.MineEntity
import com.xczcdjx.word.net.Network
import retrofit2.http.GET

interface MineService {
    @GET("user/info")
    suspend fun info(): MineEntity
    companion object{
        fun instance():MineService=Network.createService(MineService::class.java)
    }
}