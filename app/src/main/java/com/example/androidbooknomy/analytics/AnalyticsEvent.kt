package com.example.androidbooknomy.analytics

import com.google.firebase.analytics.ktx.ParametersBuilder

interface AnalyticsUseCase {
    fun log(name: String, block: ParametersBuilder.() -> Unit)
}