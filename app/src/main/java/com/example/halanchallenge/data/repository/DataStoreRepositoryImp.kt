package com.example.halanchallenge.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.halanchallenge.data.source.cache.PreferencesKeys
import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import com.example.halanchallenge.utils.extensions.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepositoryImp @Inject constructor(val context: Context) : DataStoreRepository {
    val token : Flow<String> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[PreferencesKeys.TOKEN_KEY] ?: ""
        }
    override suspend fun saveToken(tokenPreference: TokenPreferences) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN_KEY]  = tokenPreference.token
        }
    }

    override suspend fun saveCredentials(userCredentials: UserPreferences) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN]  = userCredentials.isLoggedIn
            preferences[PreferencesKeys.USER_NAME]  = userCredentials.username
            preferences[PreferencesKeys.PASSWORD]  = userCredentials.password
        }
    }

}