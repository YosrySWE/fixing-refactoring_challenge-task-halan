package com.example.halanchallenge.cache

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.halanchallenge.data.repository.DataStoreRepositoryImp
import com.example.halanchallenge.domain.repository.cache.models.TokenPreferences
import com.example.halanchallenge.utils.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataStoreTest {
    lateinit var dataStore: DataStoreRepositoryImp
    lateinit var contenxt: Context

    @Before
    fun setup() {
        contenxt = InstrumentationRegistry.getInstrumentation().targetContext
        dataStore = DataStoreRepositoryImp(contenxt)
    }

    @After
    fun clear() {
        CoroutineScope(Dispatchers.IO).launch {
            contenxt.dataStore.edit {
                it.clear()
            }
        }
    }

    @Test
    fun saveToken() {
        CoroutineScope(Dispatchers.IO).launch {
            val token =
                "Authorization: Bearer f7asfsa8asf9asf899jf9j9s99_(f9afud9dgu9dj90_fau9duf9ddfaf"
            runBlocking {
                dataStore.saveToken(TokenPreferences(token))
            }


            launch {
                assertEquals(token, dataStore.token.first())
            }

        }

    }

}
