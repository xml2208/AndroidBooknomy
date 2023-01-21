package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.music.MusicItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MusicBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var musicItem: MusicItem

    companion object {
        const val TAG = "ModalBottomSheet"
        fun newInstance(musicItem: MusicItem): MusicBottomSheetDialog {
            val args = Bundle()
            args.putParcelable("musicItem", musicItem)
            return MusicBottomSheetDialog().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {

        musicItem = arguments?.getParcelable("musicItem")!!

        setContent {
            MainScreen(musicItem = musicItem)
        }
    }

    @Composable
    fun MainScreen(musicItem: MusicItem) {
        Box(contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.bg_media_player),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                contentDescription = null
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                NetworkImage(url = musicItem.photo.imgUrl)
                Text(
                    text = musicItem.music.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = musicItem.author,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
            WithExoPlayer(
                musicUrl = musicItem.music.musicUrl,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }
//            Column(Modifier.verticalScroll(rememberScrollState())) {
//                Text(text = musicItem.fileText.fileUrl)
//            }
    }
}

@Composable
private fun WithExoPlayer(musicUrl: String, modifier: Modifier) {
    val context = LocalContext.current
    val exoPlayer =
        ExoPlayer.Builder(context)
            .build()
            .also { exoPlayer ->
                val mediaItem = MediaItem.Builder()
                    .setUri(musicUrl)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                PlayerControlView(context).apply {
                    this.player = exoPlayer
                    this.showTimeoutMs = 0
                }
            },
        )
    }
}

@Composable
fun NetworkImage(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .placeholder(R.drawable.netflix_img)
            .build(),
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .size(height = 280.dp, width = 200.dp),
        contentDescription = null
    )
}