package com.xczcdjx.word.entity

import com.squareup.moshi.Json

data class UserEntity(
    // 序列化别名注解
    @Json(name="nickname") val username:String,val avatarUrl:String)

data class MineEntity(val data:UserEntity):BaseRes()