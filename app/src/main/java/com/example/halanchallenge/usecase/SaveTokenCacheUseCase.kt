package com.example.halanchallenge.usecase

import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SaveTokenCacheUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(tokenPreferences: TokenPreferences) : Flow<Boolean> {
        return dataStoreRepository.saveToken(tokenPreferences)
    }
}