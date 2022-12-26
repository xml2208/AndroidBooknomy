package com.example.androidbooknomy

import android.app.Application
import android.util.Log
import com.example.androidbooknomy.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

class BooknomyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        Log.d("ddk9499", "App started")
        startKoin {
            printLogger()
            androidContext(this@BooknomyApp)
            modules(allModules)
        }
    }
}