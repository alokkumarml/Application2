package com.example.myapplication1

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        context = applicationContext


    }

    companion object {
        var context: Context? = null
    }

}