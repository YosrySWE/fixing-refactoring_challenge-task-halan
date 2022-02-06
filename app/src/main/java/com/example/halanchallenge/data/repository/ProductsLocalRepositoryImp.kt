package com.example.halanchallenge.data.repository

import com.example.halanchallenge.data.mappers.toDataTransferObject
import com.example.halanchallenge.data.mappers.toEntity
import com.example.halanchallenge.data.source.local.dao.ProductDao
import com.example.halanchallenge.domain.repository.local.ProductsLocalRepository
import com.example.halanchallenge.domain.repository.remote.models.Product
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsLocalRepositoryImp @Inject constructor(
    private val productDao: ProductDao
) : ProductsLocalRepository {
    override suspend fun products(): Flow<Result<List<Product>, String>> {
        return flow {
            productDao.getProducts().collect {
                try {
                    emit(Result.Success(it.map { item -> item.toDataTransferObject() }))
                } catch (e: Exception) {
                    emit(Result.Error(e.message.toString()))
                }
            }
        }
    }

    override suspend fun getProduct(id: Int): Flow<Result<Product, String>> {
        return flow {
            try {
                emit(Result.Success(productDao.getProduct(id).first().toDataTransferObject()))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    override suspend fun saveProducts(products: List<Product>) = withContext(Dispatchers.IO) {
        productDao.saveProducts(products.map { it.toEntity() })
    }

    override suspend fun deleteProduct(product: Product) = withContext(Dispatchers.IO) {
        productDao.deleteProduct(product = product.toEntity())
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        productDao.deleteAll()
    }
}