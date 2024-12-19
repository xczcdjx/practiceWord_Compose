package com.xczcdjx.word.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TestStoreManager(private val ctx: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("test_manager")
        val saveName = stringPreferencesKey("saveName")
    }

    val getName: Flow<String> = ctx.dataStore.data.map { p -> p[saveName]?:"ddd" }
    suspend fun setName(v: String) {
        ctx.dataStore.edit { p ->
            p[saveName] = v
        }
    }
}