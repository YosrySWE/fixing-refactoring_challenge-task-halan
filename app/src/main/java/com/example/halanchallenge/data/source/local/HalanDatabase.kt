package com.example.halanchallenge.data.source.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.halanchallenge.data.source.local.dao.ProductDao
import com.example.halanchallenge.data.source.local.dao.ProfileDao
import com.example.halanchallenge.data.source.local.entities.ProductEntity
import com.example.halanchallenge.data.source.local.entities.ProfileEntity
import com.example.halanchallenge.utils.Converters
import com.example.halanchallenge.utils.DATABASE_NAME

@Database(
    entities = [ProfileEntity::class, ProductEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class HalanDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile //Marks the JVM backing field of the annotated property as volatile, meaning that writes to this field are immediately made visible to other threads
        private var instance: HalanDatabase? = null
        private val LOCK = Any()
        operator fun invoke(app: Application) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(app).also { instance = it }
        }

        private fun buildDatabase(application: Application) = Room.databaseBuilder(
            application,
            HalanDatabase::class.java,
            DATABASE_NAME
        )
            .build()
    }
}
