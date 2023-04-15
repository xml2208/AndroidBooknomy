package com.example.androidbooknomy.di

import androidx.fragment.app.FragmentActivity
import com.example.androidbooknomy.R
import com.example.androidbooknomy.analytics.AnalyticsUseCaseImpl
import com.example.androidbooknomy.analytics.AnalyticsUseCase
import com.example.androidbooknomy.cicirone.BooknomyAppNavigator
import com.example.androidbooknomy.data.storage.Prefs
import com.example.androidbooknomy.network.*
import com.example.androidbooknomy.ui.feature.login.RegistrationViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.books.BooksScreenViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.EntertainmentViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list.MusicListViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.home.MainAppRepository
import com.example.androidbooknomy.ui.feature.main.main_app.home.HomeScreenViewModel
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Router
import com.google.firebase.analytics.FirebaseAnalytics
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val analyticsModule = module {
    single { FirebaseAnalytics.getInstance(androidContext()) }
    factory<AnalyticsUseCase> { AnalyticsUseCaseImpl(get()) }
}

val navigationModule = module {

    single { Cicerone.create() }

    factory {
        get<Cicerone<Router>>().router
    }

    factory<Navigator> { (activity: FragmentActivity) ->
        BooknomyAppNavigator(activity, R.id.container)
    }

    factory {
        val router = get<Cicerone<Router>>()
        router.getNavigatorHolder()
    }

}

val commonsModule = module {
    single { Prefs(get()) }
    single { AuthInterceptor(get()) }
}

val reposModule = module {
    single { MainAppRepository(get()) }
}

val viewModels = module {
    viewModel { RegistrationViewModel(provideApiService(get()), get(), get(), get()) }
    viewModel { HomeScreenViewModel(get(), get()) }
    viewModel { BooksScreenViewModel(get(), get(), get()) }
    viewModel { MusicListViewModel(get()) }
    viewModel { EntertainmentViewModel(get()) }
}

val networkModule = module {
    single { provideOkhttp(get()) }
    single { provideRetrofit(get()) }
    single { provideApiService(get()) }
}

private fun provideOkhttp(prefs: Prefs): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(HeaderInterceptor())
        .authenticator(AuthInterceptor(prefs))
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

fun provideApiService(retrofit: Retrofit): ApiClient {
    return retrofit.create(ApiClient::class.java)
}

val allModules = listOf(navigationModule, analyticsModule, commonsModule, networkModule, viewModels, reposModule)