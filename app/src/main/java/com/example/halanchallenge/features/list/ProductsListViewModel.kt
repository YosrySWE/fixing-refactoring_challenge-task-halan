package com.example.halanchallenge.features.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.halanchallenge.domain.usecase.ProductsUseCase
import com.example.halanchallenge.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase
) : ViewModel() {
    val intentChannel = Channel<ProductsIntent>(Channel.UNLIMITED)

    init {
        processIntent()

    }

    private val _state = MutableStateFlow<ProductsViewState>(ProductsViewState.Init)
    val state: StateFlow<ProductsViewState> get() = _state

    private fun setLoading() {
        _state.value = ProductsViewState.IsLoading(true)
    }

    private fun hideLoading() {
        _state.value = ProductsViewState.IsLoading(false)
    }

    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    is ProductsIntent.ProductsAction -> {
                        loadProducts()
                    }
                    is ProductsIntent.None -> _state.value = ProductsViewState.Init
                }
            }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productsUseCase()
                .onStart {
                    setLoading()
                }.onCompletion {
                    hideLoading()
                }.collect {
                    when (it) {
                        is Result.Success -> {
                            _state.value =
                                ProductsViewState.Success(it.data!!.data?.toMutableList()!!)
                        }
                        is Result.Error -> {
                            _state.value = ProductsViewState.Error(it.message)
                        }
                    }
                }
        }
    }

}