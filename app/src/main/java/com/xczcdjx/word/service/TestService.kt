package com.xczcdjx.word.service

import com.xczcdjx.word.entity.TestEntity
import com.xczcdjx.word.net.Network
import retrofit2.http.GET

interface TestService {
    @GET("test")
    suspend fun test(): TestEntity

    companion object {
        fun instance(): TestService = Network.createService(TestService::class.java)
    }
}