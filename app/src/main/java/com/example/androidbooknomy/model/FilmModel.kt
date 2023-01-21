package com.example.androidbooknomy.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

data class FilmResponse(
    @SerializedName("data") var data: List<FilmModel>
)

@Parcelize
data class FilmModel(
    val id: Int,
    val title: String,
    val duration: String,
    @SerializedName("lang_movie") val movieLanguage: String,
    @SerializedName("content") val content: String,
    @SerializedName("films") val films: @RawValue SingleFilm,
    @SerializedName("image") val image: @RawValue SingleImage
): Parcelable

@Parcelize
data class SingleFilm(
    @SerializedName("path") val filmUrl: String
): Parcelable

@Parcelize
data class SingleImage(
    @SerializedName("path") val imageUrl: String
): Parcelable