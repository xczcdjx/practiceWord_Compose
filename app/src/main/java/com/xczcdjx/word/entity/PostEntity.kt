package com.xczcdjx.word.entity

import com.squareup.moshi.Json

enum class ShowType(val t:String){
    listType("l"),gridType("g")
}
data class GridEntity(val data:DataG<RecordGrid>):BaseRes()
data class ListEntity(val data:DataG<RecordList>):BaseRes()
//data class TypeEntity<T>(val data:DataG<T>):BaseRes()
open class ResData {
    val total: Long=0
    val pageNo: Long=1
    val pageSize: Long=10
}
data class DataG<T> (
    val records: List<T>,
):ResData()
data class RecordGrid (
    val id: Long,
    val name: String,
    val sort: Long? = null,
    val createDate: String,
    val acTexts: List<ACText>
)

data class RecordList (
    val id: Long,
    val tit: String,
    val desc: String,
    val isOpen: Boolean,
    val openStr: String? = null,

    @Json(name = "imgUrl")
    val imgURL: String? = null,

    @Json(name = "linkUrl")
    val linkURL: String,

    @Json(name = "gitHubUrl")
    val gitHubURL: String? = null,

    val sort: Long? = null,
    val updateDate: String,
    val acNav: ACNav,
    val account: Account
)
data class ACNav (
    val id: Long,
    val name: String,
    val sort: Long? = null,
    val createDate: String
)

data class ACText (
    val id: Long,
    val tit: String,
    val desc: String,
    val isOpen: Boolean,
    val openStr: String? = null,

    @Json(name = "imgUrl")
    val imgURL: String? = null,

    @Json(name = "linkUrl")
    val linkURL: String,

    @Json(name = "gitHubUrl")
    val gitHubURL: String? = null,

    val sort: Long? = null,
    val updateDate: String,
    val account: Account
)


data class Account (
    val id: Long,
    val username: String,
    val info: Info
)


data class Info (
    val avatar: String? = null
)