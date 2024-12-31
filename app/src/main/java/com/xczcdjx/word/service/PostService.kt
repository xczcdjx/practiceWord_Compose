package com.xczcdjx.word.service

import com.xczcdjx.word.entity.DataG
import com.xczcdjx.word.entity.PostEntity
import com.xczcdjx.word.entity.PostLikeEntity
import com.xczcdjx.word.net.Network
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PostService {
    @GET("post/getAll")
    suspend fun search(
        @Query("page") page:Int,
        @Query("size") size:Int,
    ): PostEntity
    @GET
    suspend fun likeCon(
        @Url url:String,
        @Query("postId") postId:Long,
    ): PostLikeEntity
    companion object {
        fun instance():PostService=Network.createService(PostService::class.java)
    }
}