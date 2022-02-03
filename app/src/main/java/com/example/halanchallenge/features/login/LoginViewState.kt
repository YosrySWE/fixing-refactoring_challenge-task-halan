package com.example.halanchallenge.features.login

import com.example.halanchallenge.domain.models.Login
import com.example.halanchallenge.domain.models.Profile

sealed class LoginViewState {
    object Init : LoginViewState()
    data class IsLoading(val isLoading: Boolean): LoginViewState()
    data class SuccessLogin(val response: Profile) : LoginViewState()
    data class ErrorLogin(val error: String) : LoginViewState()
}

sealed class LoginIntent{
    data class LoginAction(val request: Login) : LoginIntent()
    object None: LoginIntent()
}
