package com.example.halanchallenge.domain.usecase

import com.example.halanchallenge.data.repository.Result
import com.example.halanchallenge.domain.models.Login
import com.example.halanchallenge.domain.models.Profile
import com.example.halanchallenge.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(request: Login): Flow<Result<Profile, String>> {
        return loginRepository.login(request)
    }
}