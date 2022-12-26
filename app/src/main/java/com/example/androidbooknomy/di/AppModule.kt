package com.example.androidbooknomy.di

import com.example.androidbooknomy.network.ApiClient
import com.example.androidbooknomy.network.authenticator
import com.example.androidbooknomy.network.headerInterceptor
import com.example.androidbooknomy.network.loggingInterceptor
import com.example.androidbooknomy.ui.feature.login.RegistrationViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.books.BooksScreenViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.home.MainAppRepository
import com.example.androidbooknomy.ui.feature.main.main_app.home.HomeScreenViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val reposModule = module {
    single { MainAppRepository(get()) }
}

val viewModels = module {
    viewModel { RegistrationViewModel(provideApiService()) }
    viewModel { HomeScreenViewModel(get()) }
    viewModel { BooksScreenViewModel(get()) }
}

val networkModule = module {
    single { provideOkhttp() }
    single { provideRetrofit(get()) }
    single { provideApiService() }
}

private fun provideOkhttp(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .authenticator(authenticator)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
}

private fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://apidis.uuuu.uz/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()
}

fun provideApiService(): ApiClient {
    return provideRetrofit(provideOkhttp()).create(ApiClient::class.java)
}

val allModules = listOf(networkModule, viewModels, reposModule)