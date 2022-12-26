package com.example.androidbooknomy.network

import com.example.androidbooknomy.model.BooksModel
import com.google.gson.JsonObject
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {

    @POST("user/send")
    suspend fun sendSmsToPhone(@Query("phone") phone: String): JSONObject

    @POST("user/approve")
    suspend fun approveUser(@Query("phone") phone: String, @Query("code") code: String): JSONObject

    @GET("books")
    suspend fun getBooks(): BooksModel

//    @GET("films")

}
