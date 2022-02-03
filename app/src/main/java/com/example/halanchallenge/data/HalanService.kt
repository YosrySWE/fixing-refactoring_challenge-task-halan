package com.example.halanchallenge.data

import com.example.halanchallenge.domain.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HalanService {
    @POST("auth")
    suspend fun login(@Body request: Login): Response<WrappedResponse<Profile>>

    @GET("products")
    suspend fun getProducts(): Response<WrappedListResponse<Product>>
}