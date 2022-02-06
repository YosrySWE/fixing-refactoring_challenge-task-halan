package com.example.halanchallenge.usecase

import androidx.test.platform.app.InstrumentationRegistry
import com.example.halanchallenge.data.repository.DataStoreRepositoryImp
import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.google.common.truth.Truth.assertThat

class GetCredentialsUseCaseTest {
    lateinit var dataStoreRepository: DataStoreRepository
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        dataStoreRepository = DataStoreRepositoryImp(appContext)
    }

    @After
    fun tearDown() {
        runBlocking { dataStoreRepository.clearAllCache() }
    }

    @Test
    operator fun invoke()
    {

    }
}