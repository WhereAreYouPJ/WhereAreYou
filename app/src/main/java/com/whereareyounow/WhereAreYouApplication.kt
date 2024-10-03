package com.whereareyounow

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WhereAreYouApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, "df48091765f93bd7bce0236bd1e775e3")
    }
    init {
        instance = this
    }

    companion object {
        lateinit var instance: WhereAreYouApplication
        fun applicationContext(): Context = instance.applicationContext
    }
}