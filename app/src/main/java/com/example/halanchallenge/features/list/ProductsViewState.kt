package com.example.halanchallenge.features.list

import com.example.halanchallenge.domain.repository.remote.models.Login
import com.example.halanchallenge.domain.repository.remote.models.Product
import com.example.halanchallenge.domain.repository.remote.models.Profile


sealed class ProductsViewState{
    data class Init(val profile: Profile) : ProductsViewState()
    object BackToLogin : ProductsViewState()
    data class IsLoading(val isLoading: Boolean) : ProductsViewState()
    data class Success(val response: MutableList<Product>) : ProductsViewState()
    data class Error(val message: String) : ProductsViewState()
    object None: ProductsViewState()
}

sealed class ProductsIntent {
    object ProductsAction : ProductsIntent()
    object RefreshToken: ProductsIntent()
    object LoadProfile: ProductsIntent()
    object None : ProductsIntent()
    data class Idle(val profile: Profile): ProductsIntent()
}
