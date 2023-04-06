package com.example.androidbooknomy

import android.app.Application
import android.util.Log
import com.example.androidbooknomy.di.allModules
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BooknomyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        Log.d("ddk9499", "App started")
        startKoin {
            printLogger()
            androidContext(this@BooknomyApp)
            modules(allModules)
        }
    }
}