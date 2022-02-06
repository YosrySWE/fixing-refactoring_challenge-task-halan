package com.example.halanchallenge.data.repository

import com.example.halanchallenge.data.mappers.toDataTransferObject
import com.example.halanchallenge.data.mappers.toEntity
import com.example.halanchallenge.data.source.local.dao.ProfileDao
import com.example.halanchallenge.domain.repository.local.LoginLocalRepository
import com.example.halanchallenge.domain.repository.remote.models.Profile
import com.example.halanchallenge.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginLocalRepositoryImp @Inject constructor(
    private val profileDao: ProfileDao,
) : LoginLocalRepository {

    override suspend fun saveProfile(profile: Profile): Flow<Long> {
        return flow {
            emit(profileDao.saveProfile(profile = profile.toEntity()))
        }
    }

    override suspend fun profile(userName: String): Flow<Result<Profile, String>> {
        return flow {
            try {
                emit(Result.Success(profileDao.getProfile(userName).first().toDataTransferObject()))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    override suspend fun deleteProfile(profile: Profile) = withContext(Dispatchers.IO) {
        profileDao.deleteProfile(profile = profile.toEntity())
    }

    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        profileDao.deleteAll()
    }
}