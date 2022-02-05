package com.example.halanchallenge.domain.repository.local

import com.example.halanchallenge.domain.repository.remote.models.Profile
import kotlinx.coroutines.flow.Flow

interface LoginLocalRepository {
    suspend fun saveProfile(profile: Profile)

    suspend fun profile(userName: String): Flow<Profile>

    suspend fun deleteProfile(profile: Profile)

    suspend fun deleteAll()


}