package com.example.halanchallenge.features.login

import com.example.halanchallenge.domain.repository.remote.models.Login
import com.example.halanchallenge.domain.repository.remote.models.Profile

sealed class LoginViewState  {
    object Init : LoginViewState()
    data class IsLoading(val isLoading: Boolean) : LoginViewState()
    data class Success(val response: Profile) : LoginViewState()
    data class Error(val message: String) : LoginViewState()
    data class NavigateToProducts(val username: String): LoginViewState()
    object IsLoggedInState: LoginViewState()
}


sealed class LoginIntent{
    data class LoginAction(val request: Login) : LoginIntent()
    object IsLoggedIn: LoginIntent()
    data class NavigateScreen(val userName: String): LoginIntent()
    object None: LoginIntent()
}
