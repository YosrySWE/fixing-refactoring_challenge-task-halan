package com.example.halanchallenge.usecase

import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetTokenPreferenceUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(): Flow<Result<String, String>> {
        return flow {
            try {
                emit(Result.Success(dataStoreRepository.getToken().first().token))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }
}