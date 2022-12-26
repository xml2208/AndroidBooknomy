package com.example.androidbooknomy.model

import com.google.gson.annotations.SerializedName

data class BooksModel(
    @SerializedName("data") val booksList: List<BookModel>
)

data class BookModel(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("price") val price: String,
    @SerializedName("sale_price") val salePrice: String,
    @SerializedName("sale_percent") val salePercent: String,
    @SerializedName("economy_price") val economyPrice: String,
    @SerializedName("photo") val photo: String
)