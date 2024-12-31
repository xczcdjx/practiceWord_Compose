package com.xczcdjx.word.entity

import com.squareup.moshi.Json

data class PostEntity(val data:DataG<PostItemEntity>):BaseRes()
data class DataG<T> (
    val records: List<T>,
):PostResData()
data class PostModelEntity(
    val page:Int=1,
    val size:Int=10,
    val total:Int=0
)
open class PostResData {
    val total: Int=0
    val page: Int=1
    val size: Int=10
}
data class PostItemEntity(
    val id: Long,
    val postText: String,
    val rightCount: Long,
    val answeredCount: Long,
    val timeUsed: Long,
    val createTime: String,
    val likeCount: Long,
    val nickname: String,

    @Json(name="avatarUrl")
    val avatarURL: String,

    val isLike: Int
)