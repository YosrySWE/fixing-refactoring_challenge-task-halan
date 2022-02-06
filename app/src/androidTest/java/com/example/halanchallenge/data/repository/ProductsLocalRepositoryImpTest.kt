package com.example.halanchallenge.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.halanchallenge.data.source.local.HalanDatabase
import com.example.halanchallenge.data.source.local.entities.ProductEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductsLocalRepositoryImpTest {

    private lateinit var database: HalanDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HalanDatabase::class.java
        ).build()

    }

    @After
    fun closeDb() = database.close()


    @Test
    fun saveProducts_getProducts() = runBlocking {
        database.productDao().saveProducts(createList())
        val loaded = database.productDao().getProducts().first()
        assertThat(loaded).hasSize(10)
    }

    @Test
    fun getProduct() = runBlocking {
        database.productDao().saveProducts(createList())
        val loaded = database.productDao().getProduct(4).first()
        assertThat(loaded).isNotNull()
        assertThat(loaded.id).isEqualTo(4)

    }

    @Test
    fun deleteProduct() = runBlocking {
        database.productDao().saveProducts(createList())
        database.productDao().deleteProduct(createList()[0])
        val loaded = database.productDao().getProduct(0).first()
        val loaded2 = database.productDao().getProduct(1).first()

        assertThat(loaded).isNull()
        assertThat(loaded2).isNotNull()
        assertThat(loaded2.id).isEqualTo(1)

    }

    @Test
    fun deleteAll() = runBlocking {
        database.productDao().saveProducts(createList())
        database.productDao().deleteAll()
        val loaded = database.productDao().getProducts().first()
        assertThat(loaded).hasSize(0)
        assertThat(loaded).isEmpty()
    }


    fun createList(): List<ProductEntity>{
        return listOf(
            ProductEntity(id = 0, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 1, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 2, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 3, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 4, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 5, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 6, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 7, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 8, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
            ProductEntity(id = 9, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") ),
        )
    }

    fun createProduct(): ProductEntity{
        return ProductEntity(id = 0, name_ar = "Ahmed", name_en = "Ahmed", image = "image.png", price = 35, brand = "tech", deal_description = "good offer", images = listOf("png1", "png2") )
    }
}