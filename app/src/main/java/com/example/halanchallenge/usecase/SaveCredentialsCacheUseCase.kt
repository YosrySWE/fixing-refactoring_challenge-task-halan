package com.example.halanchallenge.usecase

import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCredentialsCacheUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(credentials: UserPreferences): Flow<Boolean> {
        return dataStoreRepository.saveCredentials(credentials)
    }
}