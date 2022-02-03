package com.example.halanchallenge.features.list

import com.example.halanchallenge.domain.models.Product

sealed class ProductsViewState {
    object Init : ProductsViewState()
    data class IsLoading(val isLoading: Boolean) : ProductsViewState()
    data class Success(val response: MutableList<Product>) : ProductsViewState()
    data class Error(val error: String) : ProductsViewState()
}

sealed class ProductsIntent {
    object ProductsAction : ProductsIntent()
    object None : ProductsIntent()
}
