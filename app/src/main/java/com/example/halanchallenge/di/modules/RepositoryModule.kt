package com.example.halanchallenge.di.modules

import android.content.Context
import com.example.halanchallenge.data.repository.DataStoreRepositoryImp
import com.example.halanchallenge.data.repository.LoginRepositoryImp
import com.example.halanchallenge.data.repository.ProductsRepositoryImp
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
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreRepositoryImp =
        DataStoreRepositoryImp(appContext)



}