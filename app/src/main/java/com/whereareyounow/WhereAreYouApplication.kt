package com.whereareyounow

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WhereAreYouApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: WhereAreYouApplication
        fun applicationContext(): Context = instance.applicationContext
    }
}