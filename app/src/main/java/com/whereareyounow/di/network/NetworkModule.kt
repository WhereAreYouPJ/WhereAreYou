package com.whereareyounow.di.network

import com.google.gson.GsonBuilder
import com.whereareyounow.BuildConfig
import com.whereareyounow.api.FCMApi
import com.whereareyounow.api.FeedApi
import com.whereareyounow.api.FriendApi
import com.whereareyounow.api.LocationApi
import com.whereareyounow.api.MemberApi
import com.whereareyounow.api.ScheduleApi
import com.whereareyounow.api.SearchLocationApi
import com.whereareyounow.util.network.LogInterceptor
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
    fun provideHttpClient(
//        tokenInterceptor: TokenInterceptor,
//        authenticator: AuthAuthenticator,
        logInterceptor: LogInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logInterceptor)
//            .addInterceptor(tokenInterceptor)
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
    fun provideSearchLocationApi(@SearchLocationApiClass retrofit: Retrofit): SearchLocationApi {
        return retrofit.create(SearchLocationApi::class.java)
    }

    // 백엔드 서버 url
    @Singleton
    @Provides
    @RemoteAccessTokenAutoAdded
    fun provideRemoteRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // 일정 Api
    @Singleton
    @Provides
    fun provideScheduleApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }

    // 회원 Api
    @Singleton
    @Provides
    fun provideMemberApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): MemberApi {
        return retrofit.create(MemberApi::class.java)
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

    // FCM Api
    @Singleton
    @Provides
    fun provideFeedApi(@RemoteAccessTokenAutoAdded retrofit: Retrofit): FeedApi {
        return retrofit.create(FeedApi::class.java)
    }
}