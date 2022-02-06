package com.example.halanchallenge.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import com.example.halanchallenge.data.source.cache.PreferencesKeys
import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import com.example.halanchallenge.utils.extensions.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepositoryImp @Inject constructor(@ApplicationContext val context: Context) : DataStoreRepository {
    private val token : Flow<TokenPreferences> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            TokenPreferences(
                token = preferences[PreferencesKeys.TOKEN_KEY] ?: ""
            )

        }

    private val userPreferences : Flow<UserPreferences> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            UserPreferences(
                username = preferences[PreferencesKeys.USER_NAME] ?: "",
                password = preferences[PreferencesKeys.PASSWORD] ?: "",
                isLoggedIn = preferences[PreferencesKeys.IS_LOGGED_IN] ?: false
            )

        }
    override suspend fun saveToken(tokenPreference: TokenPreferences): Flow<Boolean> {
        return flow {
            val pref = context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.TOKEN_KEY]  = tokenPreference.token
            }.toPreferences()
            emit(pref.contains(PreferencesKeys.TOKEN_KEY))
        }
    }

    override suspend fun getToken(): Flow<TokenPreferences> {
        return token
    }


    override suspend fun saveCredentials(userCredentials: UserPreferences) : Flow<Boolean>{
        return flow {
            val pref = context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.IS_LOGGED_IN]  = userCredentials.isLoggedIn
                preferences[PreferencesKeys.USER_NAME]  = userCredentials.username
                preferences[PreferencesKeys.PASSWORD]  = userCredentials.password
            }.toPreferences()
            emit(pref.contains(PreferencesKeys.IS_LOGGED_IN))
        }
    }

    override suspend fun getCredentials(): Flow<UserPreferences> {
        return userPreferences
    }


}