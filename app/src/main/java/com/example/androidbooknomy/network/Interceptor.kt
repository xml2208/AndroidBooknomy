package com.example.androidbooknomy.network

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

val loggingInterceptor = HttpLoggingInterceptor().also {
    it.level = HttpLoggingInterceptor.Level.BODY
}

val headerInterceptor = Interceptor { chain ->
    var request = chain.request()
    request = request.newBuilder()
        .addHeader("Content-Type", "application/json")
        .addHeader("Accept", "application/json")
        .build()

    chain.proceed(request)
}

val authenticator = Authenticator { _, response ->
    val requestAvailable: Request?
//    val token = GithubApp.INSTANCE.prefs.token
    requestAvailable = response.request.newBuilder()
        .header("Authorization", "Bearer (token)")
        .build()
    requestAvailable
}