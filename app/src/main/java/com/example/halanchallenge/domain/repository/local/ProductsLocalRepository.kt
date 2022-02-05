package com.example.halanchallenge.domain.repository.local

import com.example.halanchallenge.domain.repository.remote.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductsLocalRepository {
    suspend fun products(): Flow<Product>
    suspend fun getProduct(id: Int): Flow<Product>
    suspend fun saveProducts(products: List<Product>)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteAll()
}