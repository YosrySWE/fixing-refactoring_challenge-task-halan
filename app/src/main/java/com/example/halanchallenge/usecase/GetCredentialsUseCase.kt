package com.example.halanchallenge.usecase

import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCredentialsUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(): Flow<Result<UserPreferences, String>> {
        return flow {
            try {
                val credentials = dataStoreRepository.getCredentials().first()
                emit(Result.Success(credentials))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }

        }
    }
}