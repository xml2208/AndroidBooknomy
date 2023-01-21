package com.example.androidbooknomy.ui.feature.main.main_app.home

import com.example.androidbooknomy.network.ApiClient

class MainAppRepository(
    private val api: ApiClient
) {
    suspend fun getAllBooks() = api.getBooks()
    suspend fun payment() = api.orderPay()

}
