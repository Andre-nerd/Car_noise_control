package ru.zoomparty.noise_controller

import android.app.Application
import android.content.Context

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this


    }

    companion object{
        lateinit var appContext:Context
    }
}