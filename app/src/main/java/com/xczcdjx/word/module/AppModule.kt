package com.xczcdjx.word.module

import com.xczcdjx.word.share.TextShare
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun tS() = TextShare { "hello" }
}