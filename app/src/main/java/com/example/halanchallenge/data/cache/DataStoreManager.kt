package com.example.halanchallenge.data.cache

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.example.halanchallenge.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(val context: Context) : DataStoreHelper {
    private val TOKEN_KEY = stringPreferencesKey("token")
    val token : Flow<String> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }
    override suspend fun saveToken(token: String) {
       context.dataStore.edit { preferences ->
           preferences[TOKEN_KEY]  = token
       }
    }

}