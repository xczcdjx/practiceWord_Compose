package com.xczcdjx.word.entity

data class UserEntity(val nickname:String,val avatarUrl:String)

data class MineEntity(val data:UserEntity):BaseRes()