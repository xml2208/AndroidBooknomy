package com.example.androidbooknomy.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.ParametersBuilder
import com.google.firebase.analytics.ktx.logEvent

class AnalyticsUseCaseImpl(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsUseCase {

    override fun log(name: String, block: ParametersBuilder.() -> Unit) {
        firebaseAnalytics.logEvent(name) {
                block()
        }
    }

    companion object {
        const val LOG_IN_EVENT = "login_event"
        const val CLICK_EVENT = "click_event"
    }
}