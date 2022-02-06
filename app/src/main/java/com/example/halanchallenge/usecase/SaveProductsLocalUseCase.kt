package com.example.halanchallenge.usecase

import com.example.halanchallenge.domain.repository.local.ProductsLocalRepository
import com.example.halanchallenge.domain.repository.remote.models.Product
import javax.inject.Inject

class SaveProductsLocalUseCase @Inject constructor(
    private val productsLocalRepository: ProductsLocalRepository
) {
    suspend operator fun invoke(products:List<Product>) {
        return productsLocalRepository.saveProducts(products)
    }
}