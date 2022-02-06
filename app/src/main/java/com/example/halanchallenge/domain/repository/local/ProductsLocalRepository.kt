package com.example.halanchallenge.domain.repository.local

import com.example.halanchallenge.domain.repository.remote.models.Product
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.flow.Flow

interface ProductsLocalRepository {
    suspend fun products(): Flow<Result<List<Product>, String>>
    suspend fun getProduct(id: Int): Flow<Result<Product, String>>
    suspend fun saveProducts(products: List<Product>)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAll()
}