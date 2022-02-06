package com.example.halanchallenge.di.modules

import com.example.halanchallenge.data.repository.DataStoreRepositoryImp
import com.example.halanchallenge.data.source.remote.HalanService
import com.example.halanchallenge.di.qualifiers.AuthOkHttpClient
import com.example.halanchallenge.di.qualifiers.NormalOkHttpClient
import com.example.halanchallenge.utils.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @AuthOkHttpClient
    @Provides
    fun provideAuthOkHttpClient(
        dataStoreRepositoryImp: DataStoreRepositoryImp
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.NONE
        val okHttpBuilder = OkHttpClient().newBuilder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.SECONDS)
            .pingInterval(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .addNetworkInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                CoroutineScope(Dispatchers.IO).launch {
                    val def = async { dataStoreRepositoryImp.getToken().first().token }
                    request.addHeader(
                            "Authorization",
                            "Bearer ${def.await()}")
                        .addHeader("Content-Type", "application/json")
                        .method(original.method, original.body)
                }

                chain.proceed(request.build())

            }

        return okHttpBuilder.build()
    }

    @NormalOkHttpClient
    @Provides
    fun provideNormalOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)

            .build()
    }


    @Provides
    fun provideLoginService(@NormalOkHttpClient okHttpClient: OkHttpClient): HalanService.LoginService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)

            .build()
            .create(HalanService.LoginService::class.java)

    }

    @Provides
    fun provideProductsService(@AuthOkHttpClient okHttpClient: OkHttpClient): HalanService.ProductsService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .build()
            .create(HalanService.ProductsService::class.java)

    }

}