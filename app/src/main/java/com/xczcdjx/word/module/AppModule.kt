package com.xczcdjx.word.module

import android.content.Context
import com.xczcdjx.word.net.Network
import com.xczcdjx.word.share.TextShare
import com.xczcdjx.word.share.UserShareView
import com.xczcdjx.word.storage.UserStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideUserStoreManager(@ApplicationContext context: Context) = UserStoreManager(context)
    @Singleton
    @Provides
    fun tS() = TextShare { "hello" }
    @Singleton
    @Provides
    // 使用已注入属性
    fun getUser(userStoreManager: UserStoreManager):UserShareView{
        val u=UserShareView(userStoreManager)
        // 懒初始化Network的userShareView
        Network.userShareView=u
        return u
    }
}