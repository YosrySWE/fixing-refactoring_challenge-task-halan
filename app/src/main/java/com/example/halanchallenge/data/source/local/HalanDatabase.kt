package com.example.halanchallenge.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.halanchallenge.data.source.local.dao.ProductDao
import com.example.halanchallenge.data.source.local.dao.ProfileDao
import com.example.halanchallenge.data.source.local.entities.ProductEntity
import com.example.halanchallenge.data.source.local.entities.ProfileEntity
import com.example.halanchallenge.di.qualifiers.ApplicationScope
import com.example.halanchallenge.utils.Converters
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Database(
    entities = [ProfileEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
@Singleton
abstract class HalanDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun profileDao(): ProfileDao

    class Callback @Inject constructor(
        private val database: Provider<HalanDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback()
}
