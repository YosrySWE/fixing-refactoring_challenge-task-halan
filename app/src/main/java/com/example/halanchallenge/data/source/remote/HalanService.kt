package com.example.halanchallenge.data.source.remote

import com.example.halanchallenge.domain.repository.remote.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HalanService {
    interface LoginService{
        @POST("auth")
        suspend fun login(@Body request: Login): Response<WrappedResponse<Profile>>
    }

    interface ProductsService{
        @GET("products")
        suspend fun getProducts(): Response<WrappedListResponse<Product>>
    }
}