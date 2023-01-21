package com.example.androidbooknomy.model.music

import com.google.gson.annotations.SerializedName

data class MusicAlbumsResponse(val data: List<MusicAlbumItem>)

data class MusicAlbumItem(@SerializedName("id") val id: Int, @SerializedName("title") val title: String, @SerializedName("files") val files: Files)

data class Files(@SerializedName("path") val imageUrl: String)