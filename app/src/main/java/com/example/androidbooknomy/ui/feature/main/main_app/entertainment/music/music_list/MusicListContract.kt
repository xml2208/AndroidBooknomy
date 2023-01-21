package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list

import com.example.androidbooknomy.model.music.MusicItem
import com.example.androidbooknomy.ui.base.CoreEffect
import com.example.androidbooknomy.ui.base.CoreEvent
import com.example.androidbooknomy.ui.base.CoreState

class MusicListContract {
    sealed class MusicListEvent: CoreEvent {
        data class OnMusicClicked(val musicItem: MusicItem): MusicListEvent()
    }

    data class MusicListState(val musicList:  List<MusicItem>): CoreState

    sealed class MusicListEffect: CoreEffect {
        object PlayMusic: MusicListEffect()
    }
}