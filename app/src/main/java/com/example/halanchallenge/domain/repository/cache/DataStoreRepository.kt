package com.example.halanchallenge.domain.repository.cache

import androidx.annotation.VisibleForTesting
import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun saveToken(tokenPreference: TokenPreferences): Flow<Boolean>

    suspend fun getToken(): Flow<TokenPreferences>

    /*
        * save LoginRequestModel -- username and password for using them to refresh login again
        * if session token is expired
        *
    */
    suspend fun saveCredentials(userCredentials: UserPreferences): Flow<Boolean>

    suspend fun getCredentials(): Flow<UserPreferences>

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    suspend fun clearAllCache()
}