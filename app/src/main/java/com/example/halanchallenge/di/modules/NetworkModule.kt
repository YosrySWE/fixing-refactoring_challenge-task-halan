package com.example.halanchallenge.di.modules

import com.example.halanchallenge.data.remote.HalanService
import com.example.halanchallenge.di.qualifiers.NormalOkHttpClient
import com.example.halanchallenge.utils.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//    @AuthOkHttpClient
//    @Provides
//    fun provideAuthOkHttpClient(
//        token: String
//    ): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.NONE
//
//        return OkHttpClient().newBuilder()
//            .readTimeout(1, TimeUnit.MINUTES)
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .pingInterval(30, TimeUnit.SECONDS)
//            .addInterceptor(interceptor)
//            .retryOnConnectionFailure(true)
//            .addNetworkInterceptor { chain ->
//                val original = chain.request()
//                val request = original.newBuilder()
//                    .addHeader("Authorization", "Bearer $token")
//                    .addHeader("Content-Type", "application/json")
//                    .method(original.method, original.body)
//                    .build()
//                chain.proceed(request)
//            }
//            .build()
//    }

    @NormalOkHttpClient
    @Provides
    fun provideNormalOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

//    @Provides
//    fun provideAuthRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): HalanService {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//            .client(okHttpClient)
//            .build()
//            .create(HalanService::class.java)
//    }

    @Provides
    fun provideHalanService(@NormalOkHttpClient okHttpClient: OkHttpClient): HalanService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
            .create(HalanService::class.java)

    }


}