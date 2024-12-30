package com.xczcdjx.word.service

import com.xczcdjx.word.entity.DataG
import com.xczcdjx.word.net.Network
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
interface PostService {
    @GET("openCdn/search")
    suspend fun search(
        @Query("pageNo") pageNo:Int?=null,
        @Query("pageSize") pageSize:Int?=null,
        @Query("keyword") keyword:String?=null,
        @Query("showType") type:String?=null,
    ): ResponseBody
    companion object {
        fun instance():PostService=Network.createService(PostService::class.java)
    }
}