package com.example.androidbooknomy.ui.feature.main.main_app.entertainment

import com.example.androidbooknomy.model.FilmModel
import com.example.androidbooknomy.model.music.MusicAlbumItem

data class EntertainmentScreenState(val filmsState: FilmsState = FilmsState(), val musicState: MusicAlbumState = MusicAlbumState()) {

    data class FilmsState(val filmItems: List<FilmModel> = emptyList())

    data class MusicAlbumState(val albums: List<MusicAlbumItem> = emptyList())

}
