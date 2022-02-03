package com.example.halanchallenge.di.modules

import com.example.halanchallenge.data.repository.LoginRepositoryImp
import com.example.halanchallenge.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun providerLoginRepository(repository: LoginRepositoryImp): LoginRepository {
        return repository
    }

}