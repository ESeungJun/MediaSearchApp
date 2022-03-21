package com.project.mediasearchapp

import android.app.Application
import com.project.mediasearchapp.utils.SharedPreferencesUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MediaSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SharedPreferencesUtils.getInstance().init(this)
    }
}