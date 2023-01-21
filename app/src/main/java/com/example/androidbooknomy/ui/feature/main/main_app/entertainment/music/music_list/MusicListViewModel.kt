package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list

import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.model.music.MusicListResponse
import com.example.androidbooknomy.network.ApiClient
import com.example.androidbooknomy.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MusicListViewModel(private val api: ApiClient) : BaseViewModel<MusicListContract.MusicListState, MusicListContract.MusicListEvent, MusicListContract.MusicListEffect>() {

    private val musicListResponse = MutableStateFlow<MusicListResponse?>(null)

    init {
        getAllMusic()
    }

    override fun setInitialState(): MusicListContract.MusicListState = MusicListContract.MusicListState(musicList = emptyList())

    override fun handleEvents(event: MusicListContract.MusicListEvent) {
        for (i in musicListResponse.value?.musicList ?: emptyList()) {
            when (event) {
                MusicListContract.MusicListEvent.OnMusicClicked(i) -> setEffect { MusicListContract.MusicListEffect.PlayMusic }
                else -> {}
            }
        }
    }

    private fun getAllMusic() {
        viewModelScope.launch {
            musicListResponse.value = api.getMusicById()

            setState {
                copy(musicList = musicListResponse.value?.musicList ?: emptyList())
            }
        }
    }




}
