package com.example.halanchallenge.domain.repository

import com.example.halanchallenge.domain.models.Product
import com.example.halanchallenge.domain.models.WrappedListResponse
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun products(): Flow<Result<WrappedListResponse<Product>, String>>
}