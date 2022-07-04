package com.devnic.gmonitoring.util

import android.app.Application

class InitShared: Application() {
    companion object{
        lateinit var shared :SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        shared = SharedPreferences(applicationContext)
    }

}