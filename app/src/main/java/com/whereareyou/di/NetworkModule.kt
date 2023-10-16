package com.whereareyou.di

import com.whereareyou.api.WhereAreYouApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
//            .baseUrl("http://yj2113.iptime.org:8383/app/")
            .baseUrl("https://a8521a7e-bc2b-436a-8b90-c88148a52ae2.mock.pstmn.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNenoonApi(retrofit: Retrofit): WhereAreYouApi {
        return retrofit.create(WhereAreYouApi::class.java)
    }
}