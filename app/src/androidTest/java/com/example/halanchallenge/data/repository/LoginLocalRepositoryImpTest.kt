package com.example.halanchallenge.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.halanchallenge.data.mappers.toEntity
import com.example.halanchallenge.data.source.local.HalanDatabase
import com.example.halanchallenge.domain.repository.remote.models.Profile
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LoginLocalRepositoryImpTest {

    private lateinit var database: HalanDatabase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            HalanDatabase::class.java
        ).build()

    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveProfile_getByUserName() = runBlocking {
        val profile = Profile(
            username = "yousry1212",
            image = "image.png",
            name = "yousry badr",
            phone = "0100000000",
            email = "yousry@gmail.com"
        )
        database.profileDao().saveProfile(profile = profile.toEntity())
        val loaded = database.profileDao().getProfile("yousry1212").first()
        assertThat(loaded).isNotNull()
        assertThat(loaded.username).isEqualTo("yousry1212")
    }

    @Test
    fun profile() = runBlocking {
        val profile = Profile(
            username = "yousry1212",
            image = "image.png",
            name = "yousry badr",
            phone = "0100000000",
            email = "yousry@gmail.com"
        )
        database.profileDao().saveProfile(profile = profile.toEntity())
        val loaded = database.profileDao().getProfile("yousry1212").first()
        assertThat(loaded).isNotNull()
        assertThat(loaded.username).isEqualTo("yousry1212")
    }

    @Test
    fun deleteProfile() = runBlocking {
        val profile = Profile(
            username = "yousry1212",
            image = "image.png",
            name = "yousry badr",
            phone = "0100000000",
            email = "yousry@gmail.com"
        )
        database.profileDao().saveProfile(profile = profile.toEntity())
        val loaded = database.profileDao().getProfile("yousry1212").first()
        database.profileDao().deleteProfile(loaded)
        val temp = database.profileDao().getProfile("yousry1212").first()
        assertThat(temp).isNull()
    }

}