package com.example.halanchallenge.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.halanchallenge.data.repository.LoginLocalRepositoryImp
import com.example.halanchallenge.data.source.local.HalanDatabase
import com.example.halanchallenge.domain.repository.local.LoginLocalRepository
import com.example.halanchallenge.domain.repository.remote.models.Profile
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class SaveProfileLocalUseCaseTest {
    lateinit var database: HalanDatabase
    lateinit var dataSource: LoginLocalRepository
    lateinit var saveProfileLocalUseCase: SaveProfileLocalUseCase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database =  Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            HalanDatabase::class.java).build()
        dataSource = LoginLocalRepositoryImp(database.profileDao())
        saveProfileLocalUseCase = SaveProfileLocalUseCase(dataSource)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun saveProfile()= runBlocking {
        val profile = Profile(
            username = "yousry1212",
            image = "image.png",
            name = "yousry badr",
            phone = "0100000000",
            email = "yousry@gmail.com"
        )
        val result = saveProfileLocalUseCase(profile)
        assertThat(result).isNotNull()
        assertThat(result).isNotEqualTo(-1)
    }

}