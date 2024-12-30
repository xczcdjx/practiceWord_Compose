package com.xczcdjx.word.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStoreManager(private val ctx:Context) {
    companion object{
        private val Context.dataStore:DataStore<Preferences> by preferencesDataStore("user_manager")
        val saveToken= stringPreferencesKey("saveToken")
    }
    val getT: Flow<String> = ctx.dataStore.data.map { p->p[saveToken]?:"" }
    suspend fun setT(v:String){
        ctx.dataStore.edit {p->
            p[saveToken]=v
        }
    }
}