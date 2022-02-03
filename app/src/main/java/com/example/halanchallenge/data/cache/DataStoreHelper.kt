package com.example.halanchallenge.data.cache

import kotlinx.coroutines.flow.Flow

interface DataStoreHelper {

    suspend fun saveToken(token: String)
}