package com.example.halanchallenge.features.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import com.example.halanchallenge.domain.repository.remote.models.Login
import com.example.halanchallenge.usecase.*
import com.example.halanchallenge.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveCredentialsCacheUseCase: SaveCredentialsCacheUseCase,
    private val saveTokenCacheUseCase: SaveTokenCacheUseCase,
    private val saveProfileLocalUseCase: SaveProfileLocalUseCase,
    private val getCredentialsUseCase: GetCredentialsUseCase,
    private val getProfileLocalUseCase: GetProfileLocalUseCase
) : ViewModel() {
    val intentChannel = Channel<LoginIntent>(Channel.UNLIMITED)

    private val job = Job()
    init {
        intentChannel.trySend(LoginIntent.IsLoggedIn)
        processIntent()

    }

    private val _state = MutableStateFlow<LoginViewState>(LoginViewState.Init)
    val state: StateFlow<LoginViewState> get() = _state

    private fun setLoading() {
        _state.value = LoginViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = LoginViewState.IsLoading(false)
    }

    private fun processIntent() {
        viewModelScope.launch(job) {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is LoginIntent.LoginAction -> {
                        login(it.request)
                    }
                    is LoginIntent.IsLoggedIn -> {
                        isLoggedInBefore()
                    }
                    is LoginIntent.NavigateScreen ->{
                        Log.i("Halan", "LoginIntent.NavigateScreen: try to fetch profile from db")
                        loadProfileLocally(it.userName)
                    }
                    is LoginIntent.None -> Unit
                }
            }
        }
    }

    private fun loadProfileLocally(userName: String){
        viewModelScope.launch(job)  {
            getProfileLocalUseCase(userName)
                .onStart { setLoading() }
                .onCompletion { hideLoading() }
                .collect {
                    when(it){
                        is Result.Success ->{
                            Log.i("Halan", "loadProfileLocally: Success ${it.data}")
                            _state.value = LoginViewState.Success(it.data!!)
                        }
                        is Result.Error ->{
                            Log.e("Halan", "loadProfileLocally: Error ${it.message}")
                            _state.value = LoginViewState.Error(it.message)
                        }
                    }
                }
        }
    }

    private fun isLoggedInBefore(){
        viewModelScope.launch(job)  {
            getCredentialsUseCase()
                .onStart {
                    setLoading()
                }.collect { item ->
                    hideLoading()
                    withContext(Dispatchers.Main){
                        when(item){
                            is Result.Success ->{
                                Log.i("Halan", "LoginIntent.IsLoggedIn done ${item.data?.isLoggedIn}")
                                _state.value = LoginViewState.NavigateToProducts(item.data?.username!!)
                            }
                            is Result.Error ->{
                                Log.i("Halan", "LoginIntent.IsLoggedIn error ${item.message}")
                                _state.value = LoginViewState.Error(item.message)

                            }
                        }
                    }
                }
        }
    }

    fun login(loginRequest: Login) {
        viewModelScope.launch(job)  {
            loginUseCase(request = loginRequest)
                .onStart {
                    setLoading()
                }.onCompletion {
                    hideLoading()
                }.collect {
                    when (it) {
                        is Result.Success -> {
                            val isTokenSaved =async { saveTokenCacheUseCase(TokenPreferences(it.data?.token!!)).first()}
                            val isCredentialsSaved = async {
                                 saveCredentialsCacheUseCase(UserPreferences(loginRequest.userName, loginRequest.password, true)).first()
                            }
                            val isProfileSaved = async { saveProfileLocalUseCase(it.data?.data!!).first() }
                            if (isTokenSaved.await() && isCredentialsSaved.await() && isProfileSaved.await() != -1L) {
                                _state.value = LoginViewState.Success(it.data?.data!!)
                            }else{
                                _state.value = LoginViewState.Error("Ops, please try again.")
                            }
                        }
                        is Result.Error -> {
                            _state.value = LoginViewState.Error(it.message)
                        }
                    }
                }
        }
    }

    fun clear(){
        job.cancel()
    }

}


