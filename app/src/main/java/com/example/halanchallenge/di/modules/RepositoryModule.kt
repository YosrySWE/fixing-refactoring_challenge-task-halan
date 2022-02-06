package com.example.halanchallenge.di.modules

import android.content.Context
import com.example.halanchallenge.data.repository.*
import com.example.halanchallenge.domain.repository.cache.DataStoreRepository
import com.example.halanchallenge.domain.repository.local.LoginLocalRepository
import com.example.halanchallenge.domain.repository.local.ProductsLocalRepository
import com.example.halanchallenge.domain.repository.remote.LoginRepository
import com.example.halanchallenge.domain.repository.remote.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideLoginRepository(repository: LoginRepositoryImp): LoginRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideProductsRepository(repository: ProductsRepositoryImp): ProductsRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideLoginLocalRepository(repository: LoginLocalRepositoryImp): LoginLocalRepository {
        return repository
    }

    @Provides
    @Singleton
    fun provideProductsLocalRepository(repository: ProductsLocalRepositoryImp): ProductsLocalRepository {
        return repository
    }



    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext appContext: Context): DataStoreRepository =
        DataStoreRepositoryImp(appContext)

}