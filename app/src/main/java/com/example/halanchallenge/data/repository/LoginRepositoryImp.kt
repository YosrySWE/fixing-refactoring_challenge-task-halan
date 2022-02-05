package com.example.halanchallenge.data.repository

import com.example.halanchallenge.data.source.remote.HalanService
import com.example.halanchallenge.domain.repository.remote.models.Login
import com.example.halanchallenge.domain.repository.remote.models.Profile
import com.example.halanchallenge.domain.repository.remote.models.WrappedResponse
import com.example.halanchallenge.domain.repository.remote.LoginRepository
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImp @Inject constructor(
    private val loginService: HalanService.LoginService,
) : LoginRepository {
    override suspend fun login(loginRequest: Login): Flow<Result<WrappedResponse<Profile>, String>> {
        return flow {
            val api = loginService.login(loginRequest)
            if (api.isSuccessful) {

                val response = api.body() as WrappedResponse<Profile>

                if (response.status == "OK" && !response.token.isNullOrEmpty()) {
                    emit(Result.Success(response))
                } else {
                    emit(Result.Error(response.message))
                }
            } else {
                if (api.code() == 400) {
                    // Bad Request
                    emit(Result.Error("Bad username, username must be 6 : 15 char length"))
                } else {
                    emit(Result.Error(api.message()))
                }
            }
        }
    }

}