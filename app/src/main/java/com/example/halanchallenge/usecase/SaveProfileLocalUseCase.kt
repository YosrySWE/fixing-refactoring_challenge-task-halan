package com.example.halanchallenge.usecase

import com.example.halanchallenge.domain.repository.local.LoginLocalRepository
import com.example.halanchallenge.domain.repository.remote.models.Profile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveProfileLocalUseCase @Inject constructor(
    private val loginLocalRepository: LoginLocalRepository
) {
    suspend operator fun invoke(profile: Profile): Flow<Long> {
        return loginLocalRepository.saveProfile(profile)
    }
}