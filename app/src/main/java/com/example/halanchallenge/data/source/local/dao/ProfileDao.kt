package com.example.halanchallenge.data.source.local.dao

import androidx.room.*
import com.example.halanchallenge.data.source.local.entities.ProfileEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile( profile: ProfileEntity)

    @Query("SELECT * FROM profile WHERE username = :userName")
    suspend fun getProfile(userName: String): Flow<ProfileEntity>

    @Delete
    suspend fun deleteProfile( profile: ProfileEntity)

    @Query("DELETE FROM profile")
    suspend fun deleteAll()
}