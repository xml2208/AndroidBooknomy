package com.example.androidbooknomy.ui.feature.main.main_app.entertainment

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.network.ApiClient
import kotlinx.coroutines.launch

class EntertainmentViewModel(private val api: ApiClient) : ViewModel() {

    private val _state: MutableState<EntertainmentScreenState> = mutableStateOf(
        EntertainmentScreenState(
            EntertainmentScreenState.FilmsState(emptyList()),
            EntertainmentScreenState.MusicAlbumState(
                emptyList()
            )
        )
    )
    val state: State<EntertainmentScreenState> = _state

    init {
        getAll()
    }

    private fun getAll() {
        viewModelScope.launch {
            _state.value = EntertainmentScreenState(
                filmsState = EntertainmentScreenState.FilmsState(filmItems = api.getFilms().data),
                musicState = EntertainmentScreenState.MusicAlbumState(albums = api.getMusicAlbum().data)
            )
        }
    }
}