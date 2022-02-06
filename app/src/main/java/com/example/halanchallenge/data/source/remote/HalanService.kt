package com.example.halanchallenge.data.source.remote

import android.app.Application
import android.content.Context
import com.example.halanchallenge.domain.repository.remote.models.*
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import retrofit2.http.*

interface HalanService {

    interface LoginService{
        @POST("auth")
        suspend fun login(@Body request: Login): Response<WrappedResponse<Profile>>
    }

    interface ProductsService{
        @GET("products")
        suspend fun getProducts(@Header("Authorization") token: String = ""): Response<WrappedListResponse<Product>>
    }
}