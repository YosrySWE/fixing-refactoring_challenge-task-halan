package com.example.halanchallenge.domain.repository.local

import com.example.halanchallenge.domain.repository.remote.models.Profile
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.flow.Flow

interface LoginLocalRepository {
    suspend fun saveProfile(profile: Profile): Flow<Long>

    suspend fun profile(userName: String): Flow<Result<Profile, String>>

    suspend fun deleteProfile(profile: Profile)

    suspend fun deleteAll()


}