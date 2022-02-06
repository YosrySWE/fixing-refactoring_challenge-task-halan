package com.example.halanchallenge.usecase

import com.example.halanchallenge.domain.repository.local.ProductsLocalRepository
import com.example.halanchallenge.domain.repository.remote.models.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.halanchallenge.utils.Result


class GetProductLocalUseCase @Inject constructor(
    private val productsLocalRepository: ProductsLocalRepository
) {
    suspend operator fun invoke(id: Int) : Flow<Result<Product, String>> {
       return productsLocalRepository.getProduct(id)
    }
}