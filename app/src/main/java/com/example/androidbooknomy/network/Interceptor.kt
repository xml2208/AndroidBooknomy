package com.example.androidbooknomy.network

import android.util.Log
import com.example.androidbooknomy.data.storage.Prefs
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor

val loggingInterceptor = HttpLoggingInterceptor().also {
    it.level = HttpLoggingInterceptor.Level.BODY
}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("ddk9499", "intercept: inside header interceptor")
        val newRequest = chain.request()
            .newBuilder()
            .addHeader("ddk9499", "proverka")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()
        Log.d("ddk9499", "intercept: $newRequest")
        return chain.proceed(newRequest)
    }
}

val headerInterceptor = Interceptor { chain ->
    return@Interceptor chain.proceed(
        chain.request()
            .newBuilder()
            .addHeader("ddk9499", "proverka")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .build()
    )
}

class AuthInterceptor(private val prefs: Prefs) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        return response.request.newBuilder()
            .addHeader("Authorization", "Bearer ${prefs.token}")
            .build()
    }
}