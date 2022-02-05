package com.example.halanchallenge.domain.usecase

import com.example.halanchallenge.domain.repository.remote.models.Product
import com.example.halanchallenge.domain.repository.remote.models.WrappedListResponse
import com.example.halanchallenge.domain.repository.remote.ProductsRepository
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend operator fun invoke(): Flow<Result<WrappedListResponse<Product>, String>>{
        return productsRepository.products()
    }
}