package com.xczcdjx.word.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class VMFactory(private val ctx:Context):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewmodel::class.java)){
            return HomeViewmodel(ctx) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}