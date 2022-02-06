package com.example.halanchallenge.usecase

import com.example.halanchallenge.domain.repository.local.LoginLocalRepository
import com.example.halanchallenge.domain.repository.remote.models.Profile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.halanchallenge.utils.Result


class GetProfileLocalUseCase @Inject constructor(
    private val loginLocalRepository: LoginLocalRepository
) {
    suspend operator fun invoke(userName: String) : Flow<Result<Profile, String>> {
       return loginLocalRepository.profile(userName)
    }
}