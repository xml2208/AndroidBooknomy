package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.music.MusicItem
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.film.PlayerView

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MusicBottomSheet(musicItem: MusicItem, mainContent: @Composable () -> Unit) {

    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.HalfExpanded)

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
        sheetContent = { BottomSheetScreen(musicItem) },
        sheetElevation = 70.dp
    ) {
        mainContent()
    }
}


@Composable
fun BottomSheetScreen(musicItem: MusicItem) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.bg_audio),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            PlayerView(mediaUrl = musicItem.music.musicUrl)
        }
    }