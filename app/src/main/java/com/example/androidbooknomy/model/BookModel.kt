package com.example.androidbooknomy.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class BooksModel(
    @SerializedName("data") val booksList: List<BookModel>
)

@Parcelize
data class BookModel(
    @SerializedName("id") val id: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("content") val content: String = "",
    @SerializedName("price") val price: String = "",
    @SerializedName("sale_price") val salePrice: String = "",
    @SerializedName("sale_percent") val salePercent: String = "",
    @SerializedName("economy_price") val economyPrice: String = "",
    @SerializedName("photo") val photo: PhotoBook = PhotoBook("")
): Parcelable

@Parcelize
data class PhotoBook(
    @SerializedName("path") val photoUrl: String
): Parcelable