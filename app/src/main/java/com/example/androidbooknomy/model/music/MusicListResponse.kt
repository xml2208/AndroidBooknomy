package com.example.androidbooknomy.model.music

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicListResponse(@SerializedName("data") val musicList: List<MusicItem> = emptyList()): Parcelable

@Parcelize
data class MusicItem(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("photo") val photo: MusicPhoto = MusicPhoto(),
    @SerializedName("author") val author: String = "",
    @SerializedName("file_text") val fileText: FileText = FileText(),
    @SerializedName("music") val music: Music = Music()
    ): Parcelable

@Parcelize
data class MusicPhoto(@SerializedName("path") val imgUrl: String = ""): Parcelable
@Parcelize
data class FileText(@SerializedName("path") val fileUrl: String = "", @SerializedName("title") val title: String = ""): Parcelable
@Parcelize
data class Music(@SerializedName("title") val title: String = "", @SerializedName("path") val musicUrl: String = ""): Parcelable
