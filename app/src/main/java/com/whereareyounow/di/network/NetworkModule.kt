package com.whereareyounow.di.network

import com.whereareyounow.BuildConfig
import com.whereareyounow.api.FCMApi
import com.whereareyounow.api.FriendApi
import com.whereareyounow.api.LocationApi
import com.whereareyounow.api.ScheduleApi
import com.whereareyounow.api.SearchLocationApi
import com.whereareyounow.api.SignInApi
import com.whereareyounow.api.SignUpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class SearchLocationApiClass

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NaverMapApi

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteAccessTokenAutoAdded

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteHeaderManuallyAdded


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    // 네이버 검색 url
    @Singleton
    @Provides
    @SearchLocationApiClass
    fun provideSearchLocationRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 네이버 검색 Api
    @Singleton
    @Provides
    fun provideSearchLocationApi(@SearchLocationApiClass retrofit: Retrofit): com.whereareyounow.api.SearchLocationApi {
        return retrofit.create(SearchLocationApi::class.java)
    }

    // 백엔드 서버 url
    @Singleton
    @Provides
    @RemoteAccessTokenAutoAdded
    fun provideRemoteRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 일정 Api
    @Singleton
    @Provides
    fun provideScheduleApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }

    // 회원가입 Api
    @Singleton
    @Provides
    fun provideSignUpApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): SignUpApi {
        return retrofit.create(SignUpApi::class.java)
    }

    // 로그인 Api
    @Singleton
    @Provides
    fun provideSignInApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): SignInApi {
        return retrofit.create(SignInApi::class.java)
    }

    // 친구 Api
    @Singleton
    @Provides
    fun provideFriendApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): FriendApi {
        return retrofit.create(FriendApi::class.java)
    }

    // 위치 공유 Api
    @Singleton
    @Provides
    fun provideLocationApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): LocationApi {
        return retrofit.create(LocationApi::class.java)
    }

    // FCM Api
    @Singleton
    @Provides
    fun provideFCMApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): FCMApi {
        return retrofit.create(FCMApi::class.java)
    }
}