package com.example.androidbooknomy.network

import com.example.androidbooknomy.model.BooksModel
import com.example.androidbooknomy.model.FilmResponse
import com.example.androidbooknomy.model.music.MusicAlbumsResponse
import com.example.androidbooknomy.model.music.MusicListResponse
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {

    @POST("user/send")
    suspend fun sendSmsToPhone(@Query("phone") phone: String): JSONObject

    @POST("user/approve")
    suspend fun approveUser(@Query("phone") phone: String, @Query("code") code: String): Map<String, Any>

    @GET("books")
    suspend fun getBooks(@Query("filter[default]") filter: String = "2",@Query("include") photo: String = "photo"): BooksModel

    @POST("order/pay")
    suspend fun orderPay(): Map<Any, Any>

    @GET("films")
    suspend fun getFilms(@Query("include") film: String = "films,image"): FilmResponse

    @GET("album")
    suspend fun getMusicAlbum(): MusicAlbumsResponse

    @GET("audio")
    suspend fun getMusicById(@Query("include") include: String = "music,photo,file_text", @Query("filter[album_id]") albumId: Int = 6): MusicListResponse
}
