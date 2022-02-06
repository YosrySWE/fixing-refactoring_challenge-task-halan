package com.example.halanchallenge.data.source.local.dao

import androidx.room.*
import com.example.halanchallenge.data.source.local.entities.ProductEntity
import com.example.halanchallenge.domain.repository.remote.models.Product
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = ProductEntity::class)
    suspend fun saveProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM product WHERE id = :id")
    fun getProduct(id: Int): Flow<ProductEntity>

    @Query("SELECT * FROM product WHERE 1")
    fun getProducts(): Flow<MutableList<ProductEntity>>

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("DELETE FROM product")
    suspend fun deleteAll()
}