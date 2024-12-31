package com.xczcdjx.word.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.xczcdjx.word.entity.PostItemEntity
import com.xczcdjx.word.entity.PostModelEntity
import com.xczcdjx.word.service.PostService
import com.xczcdjx.word.share.UserShareView
import com.xczcdjx.word.utils.LoggerUtil
import com.xczcdjx.word.utils.safeService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(val user: UserShareView) : ViewModel() {
    private val _uiState = mutableStateListOf<PostItemEntity>()
    val uiState: List<PostItemEntity> = _uiState
    var pageModel by mutableStateOf(PostModelEntity())
    private val instance = PostService.instance()
    suspend fun fetData(cb:()->Unit={}) {
        val (page, size) = pageModel
        val res = safeService { instance.search(page, size) }
        res.success?.let { d ->
//            _uiState.value = _uiState.value.toMutableList().apply { addAll(d.data.records) }
            _uiState.addAll(d.data.records)
            pageModel=pageModel.copy(page = page + 1, size = d.data.size, total = d.data.total)
            cb()
        }?:run {
            LoggerUtil.error(res.error)
        }
    }
    fun resetD(){
        pageModel=PostModelEntity()
        _uiState.clear()
    }
    suspend fun likeControl(f:Boolean,id:Long,lc:Long){
        val res = safeService { instance.likeCon(if(!f)"like/create" else "like/cancel", id) }
        res.success?.let { d ->
            /*val item = _uiState.find { it.id == id }
            item?.let {
                // 直接修改它的属性
                it.isLike = if (f) 0 else 1
            }*/
            val index=_uiState.indexOfFirst { id==it.id }
            if (index!=-1){
                _uiState[index]=_uiState[index].copy(isLike = if (f) 0 else 1, likeCount = if (!f) lc+1 else lc-1)
            }
        }
    }
}