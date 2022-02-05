package com.example.halanchallenge.features.login
import androidx.lifecycle.*
import com.example.halanchallenge.data.repository.DataStoreRepositoryImp
import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import com.example.halanchallenge.utils.Result
import com.example.halanchallenge.domain.repository.remote.models.Login
import com.example.halanchallenge.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    val dataStoreRepositoryImp: DataStoreRepositoryImp
) : ViewModel() {
    val intentChannel = Channel<LoginIntent>(Channel.UNLIMITED)

    init {
        intentChannel.trySend(LoginIntent.None)
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
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is LoginIntent.LoginAction -> {
                        login(it.request)
                    }
                    is LoginIntent.None -> Unit
                }
            }
        }
    }

    fun login(loginRequest: Login) {
        viewModelScope.launch {
            loginUseCase(request = loginRequest)
                .onStart {
                    setLoading()
                }.onCompletion {
                    hideLoading()
                }.collect {
                    when (it) {
                        is Result.Success -> {
                            launch {
                                dataStoreRepositoryImp.saveToken(TokenPreferences(it.data?.token!!))
                                dataStoreRepositoryImp.saveCredentials(UserPreferences(loginRequest.userName, loginRequest.password, isLoggedIn = true))
                            }
                            _state.value = LoginViewState.Success(it.data?.data!!)
                        }
                        is Result.Error -> {
                            _state.value = LoginViewState.Error(it.message)
                        }
                    }
                }
        }
    }


}


