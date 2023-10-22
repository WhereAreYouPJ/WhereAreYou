package com.whereareyou.di

import com.whereareyou.api.SearchLocationApi
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
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SearchLocationAPIClass

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverMapAPIClass

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class WhereAreYouApiClass

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    // 지역 검색 api
    @Singleton
    @Provides
    @SearchLocationAPIClass
    fun provideSearchLocationRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchLocationApi(@SearchLocationAPIClass retrofit: Retrofit): SearchLocationApi {
        return retrofit.create(SearchLocationApi::class.java)
    }

    // 백엔드 서버 api
    @Singleton
    @Provides
    @WhereAreYouApiClass
    fun provideWhereAreYouRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://yj2113.iptime.org:8383/app/")
//            .baseUrl("https://a8521a7e-bc2b-436a-8b90-c88148a52ae2.mock.pstmn.io/")
//            .baseUrl("http://WhereAreYou-env-v11.eba-ex8qguf6.ap-northeast-2.elasticbeanstalk.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideWhereAreYouApi(@WhereAreYouApiClass retrofit: Retrofit): WhereAreYouApi {
        return retrofit.create(WhereAreYouApi::class.java)
    }
}