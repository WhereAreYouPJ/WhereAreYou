package com.onmyway

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OnMyWayApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: OnMyWayApplication
        fun applicationContext(): Context = instance.applicationContext
    }
}