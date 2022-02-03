package com.example.halanchallenge.data.repository

import com.example.halanchallenge.data.HalanService
import com.example.halanchallenge.domain.models.Login
import com.example.halanchallenge.domain.models.Profile
import com.example.halanchallenge.domain.models.WrappedResponse
import com.example.halanchallenge.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImp @Inject constructor(
    private val loginService: HalanService
) : LoginRepository{
    override suspend fun login(loginRequest: Login): Flow<Result<Profile, String>> {
        return flow {
            val api = loginService.login(loginRequest)
            if(api.isSuccessful){
                val response = api.body() as WrappedResponse<Profile>
                if(response.status =="OK" && response.token.isNotEmpty()){
                    emit(Result.Success(response.data!!))
                }else{
                    emit(Result.Error(response.message))
                }
            }else{
                emit(Result.Error(api.errorBody().toString()))
            }
        }
    }

}