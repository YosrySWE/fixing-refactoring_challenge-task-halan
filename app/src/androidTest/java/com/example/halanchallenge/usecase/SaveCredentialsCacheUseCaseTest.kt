package com.example.halanchallenge.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.halanchallenge.data.repository.DataStoreRepositoryImp
import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import com.example.halanchallenge.domain.repository.cache.models.UserPreferences
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SaveCredentialsCacheUseCaseTest {

    lateinit var dataStoreRepository: DataStoreRepository
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp(){
        dataStoreRepository = DataStoreRepositoryImp(appContext)

    }

    @After
    fun tearDown(){

    }
    @Test
    operator fun invoke() = runBlocking{
        val request = UserPreferences(username = "UserName12112", password = "123456789", isLoggedIn = true)
        val result = dataStoreRepository.saveCredentials(request).first()
        assertThat(result).isTrue()
    }

    @Test
    fun getCredentialsFromDataStore(){
        runBlocking {
            val result = dataStoreRepository.getCredentials().first()
            assertThat(result.isLoggedIn).isTrue()
            assertThat(result.username).isNotEmpty()
        }
    }



}