package com.example.halanchallenge.domain.repository.remote

import com.example.halanchallenge.utils.Result
import com.example.halanchallenge.domain.repository.remote.models.Login
import com.example.halanchallenge.domain.repository.remote.models.Profile
import com.example.halanchallenge.domain.repository.remote.models.WrappedResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest: Login): Flow<Result<WrappedResponse<Profile>, String>>
}