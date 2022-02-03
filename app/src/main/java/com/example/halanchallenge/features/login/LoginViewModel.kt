package com.example.halanchallenge.features.login
import androidx.lifecycle.*
import com.example.halanchallenge.data.cache.DataStoreManager
import com.example.halanchallenge.utils.Result
import com.example.halanchallenge.domain.models.Login
import com.example.halanchallenge.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    val dataStoreManager: DataStoreManager
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
                                dataStoreManager.saveToken(it.data?.token!!)
                            }
                            _state.value = LoginViewState.SuccessLogin(it.data?.data!!)
                        }
                        is Result.Error -> {
                            _state.value = LoginViewState.ErrorLogin(it.message)
                        }
                    }
                }
        }
    }


}


