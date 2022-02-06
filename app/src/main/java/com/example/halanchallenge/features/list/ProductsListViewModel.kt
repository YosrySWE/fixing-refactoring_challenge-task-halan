package com.example.halanchallenge.features.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import com.example.halanchallenge.domain.repository.remote.models.Login
import com.example.halanchallenge.features.login.LoginViewState
import com.example.halanchallenge.usecase.*
import com.example.halanchallenge.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase,
    private val getCredentialsUseCase: GetCredentialsUseCase,
    private val loginUseCase: LoginUseCase,
    private val saveTokenCacheUseCase: SaveTokenCacheUseCase,
    private val saveProfileLocalUseCase: SaveProfileLocalUseCase,
    private val getProfileLocalUseCase: GetProfileLocalUseCase

) : ViewModel() {
    val intentChannel = Channel<ProductsIntent>(Channel.UNLIMITED)

    private val job = Job()
    init {
        processIntent()
    }

    private val _state = MutableStateFlow<ProductsViewState>(ProductsViewState.None)
    val state: StateFlow<ProductsViewState> get() = _state

    private fun showLoading() {
        _state.value = ProductsViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = ProductsViewState.IsLoading(false)
    }

    private fun processIntent() {
        viewModelScope.launch(job) {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is ProductsIntent.Idle ->{
                        _state.value = ProductsViewState.Init(profile = it.profile)
                    }
                    is ProductsIntent.LoadProfile ->{
                        getCredentials(false)
                    }
                    is ProductsIntent.ProductsAction -> {
                        loadProducts()
                    }
                    is ProductsIntent.RefreshToken ->{
                        getCredentials(true)
                    }
                    is ProductsIntent.None -> _state.value = ProductsViewState.None
                }
            }
        }
    }

    private fun getCredentials(isExpired: Boolean = false){
        viewModelScope.launch(job) {
            getCredentialsUseCase()
                .onStart { showLoading() }
                .onEach {  hideLoading()}
                .collect {
                    when(it){
                        is Result.Success ->{
                            if(isExpired){
                                Log.i("Halan", "try to refresh token with auto login")
                                automaticLoginToRefreshToken(Login(it.data?.username!!, it.data.password))
                            }else{

                                loadProfile(it.data?.username!!)
                            }
                        }
                        is Result.Error ->{
                            _state.value = ProductsViewState.BackToLogin
                        }
                    }
                }
        }
    }

    private fun loadProducts(token: String = "") {
        viewModelScope.launch(job) {
            productsUseCase(token).shareIn(viewModelScope, SharingStarted.Eagerly, 4)
                .onStart { showLoading() }
                .catch { Log.e("Halan", "Error when trying to load products with Exception ${it.message}") }
                .onCompletion { if(it == null) hideLoading() }
                .collect {
                    when (it) {
                        is Result.Success -> {
                            Log.i("Halan", "try to load products is Success with list size ${it.data?.data?.size}.")
                            _state.value =
                                ProductsViewState.Success(it.data!!.data?.toMutableList()!!)
                        }
                        is Result.Error -> {
                            Log.e("Halan", "Error when trying to load products with message ${it.message}")
                            _state.value = ProductsViewState.Error(it.message)
                        }
                    }
                }
        }
    }

    private fun automaticLoginToRefreshToken(request: Login){
        viewModelScope.launch(job) {
            loginUseCase(request)
                .onStart { showLoading() }
                .onEach {  hideLoading()}
                .catch { Log.e("Halan", "Error in auto login with Exception ${it.message}") }
                .collect {
                    when(it){
                        is Result.Success ->{
                            Log.i("Halan", "try to save new token ...")

                            saveToken(TokenPreferences(it.data?.token!!))
                        }
                        is Result.Error ->{
                            Log.e("Halan", "Error in auto login with message: ${it.message}")

                            _state.value = ProductsViewState.BackToLogin
                        }
                    }
                }
        }
    }

    private fun saveToken(tokenPref : TokenPreferences){
        viewModelScope.launch(job) {
            saveTokenCacheUseCase(tokenPref)
                .catch { Log.i("Halan", "Error when trying to save token with Exception ${it.message}") }
                .collect {
                    if(it){
                        Log.i("Halan", "try to load products with a new token ... ${tokenPref.token}")
                        loadProducts(token = tokenPref.token)
                    }else{
                        Log.e("Halan", "Back to Login ")

                        _state.value = ProductsViewState.BackToLogin
                    }
                }
        }
    }

    private fun loadProfile(userName: String){
        viewModelScope.launch(job) {
            getProfileLocalUseCase(userName)
                .onStart { showLoading() }
                .onEach { hideLoading() }
                .collect {
                    when(it){
                        is Result.Success->{
                            _state.value = ProductsViewState.Init(it.data!!)
                        }
                        is Result.Error ->{
                            _state.value = ProductsViewState.BackToLogin
                        }
                    }
                }
        }
    }

    fun clear(){
        job.cancel()
    }

}