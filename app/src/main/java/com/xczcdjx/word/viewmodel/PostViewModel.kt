package com.xczcdjx.word.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
    private val _uiState = MutableStateFlow(mutableListOf<PostItemEntity>())
    val uiState: StateFlow<List<PostItemEntity>> = _uiState.asStateFlow()
    val pageModel by mutableStateOf(PostModelEntity())
    private val instance = PostService.instance()
    suspend fun fetData() {
        val (page, size) = pageModel
        val res = safeService { instance.search(page, size) }
        res.success?.let { d ->
            _uiState.value = _uiState.value.toMutableList().apply { addAll(d.data.records) }
            println(_uiState.value)
            pageModel.copy(page = page + 1, size = d.data.size, total = d.data.total)
        }?:run {
            LoggerUtil.error(res.error)
        }
    }
}