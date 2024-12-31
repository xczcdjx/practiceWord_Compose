package com.xczcdjx.word.service

import com.xczcdjx.word.entity.DataG
import com.xczcdjx.word.entity.PostEntity
import com.xczcdjx.word.net.Network
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
interface PostService {
    @GET("post/getAll")
    suspend fun search(
        @Query("page") page:Int,
        @Query("size") size:Int,
    ): PostEntity
    companion object {
        fun instance():PostService=Network.createService(PostService::class.java)
    }
}