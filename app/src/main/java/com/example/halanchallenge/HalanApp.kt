package com.example.halanchallenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HalanApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}