package com.example.halanchallenge.di.modules

import android.app.Application
import androidx.room.Room
import com.example.halanchallenge.data.source.local.HalanDatabase
import com.example.halanchallenge.data.source.local.dao.ProductDao
import com.example.halanchallenge.data.source.local.dao.ProfileDao
import com.example.halanchallenge.di.qualifiers.ApplicationScope
import com.example.halanchallenge.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: HalanDatabase.Callback): HalanDatabase{
        return Room.databaseBuilder(application, HalanDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }
    @Provides
    fun provideProductDao(db: HalanDatabase): ProductDao {
        return db.productDao()
    }


    @Provides
    fun provideProfileDao(db: HalanDatabase): ProfileDao {
        return db.profileDao()
    }
}