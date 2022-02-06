package com.example.halanchallenge.domain.repository.remote

import com.example.halanchallenge.domain.repository.remote.models.Product
import com.example.halanchallenge.domain.repository.remote.models.WrappedListResponse
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun products(token: String): Flow<Result<WrappedListResponse<Product>, String>>
}