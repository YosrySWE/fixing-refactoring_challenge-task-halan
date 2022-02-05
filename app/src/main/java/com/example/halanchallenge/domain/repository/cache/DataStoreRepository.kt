package com.example.halanchallenge.domain.repository.cache

import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences

interface DataStoreRepository {

    suspend fun saveToken(tokenPreference: TokenPreferences)

    /*
        * save LoginRequestModel -- username and password for using them to refresh login again
        * if session token is expired
        *
    */
    suspend fun saveCredentials(userCredentials: UserPreferences)
}