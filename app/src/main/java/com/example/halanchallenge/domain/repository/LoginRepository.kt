package com.example.halanchallenge.domain.repository

import com.example.halanchallenge.data.repository.Result
import com.example.halanchallenge.domain.models.Login
import com.example.halanchallenge.domain.models.Profile
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginRequest: Login): Flow<Result<Profile, String>>
}