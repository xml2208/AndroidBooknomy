package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.dirror.lyricviewx.LyricViewX
import com.dirror.lyricviewx.OnPlayClickListener
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.music.MusicItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicBottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var musicItem: MusicItem
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerView: PlayerControlView
    private lateinit var lyricsView: LyricViewX
    private var isPaused = false
    private var position = 0L
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

        lifecycleScope.launch(context = Dispatchers.IO) { }
        exoPlayer = ExoPlayer.Builder(context)
            .build()
            .also { exoPlayer ->
                val mediaItem = MediaItem.Builder()
                    .setUri(musicItem.music.musicUrl)
                    .build()
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.play()
            }

        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (isPlaying) {
                    isPaused = false
                    playerView.postDelayed({ position = exoPlayer.currentPosition }, 200)
                } else {
                    isPaused = true
                    playerView.postDelayed({ position = exoPlayer.currentPosition }, 200)
                }
            }
        })

        setContent {
            MainScreen(musicItem = musicItem)
        }
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.pause()
    }

    @Composable
    fun MainScreen(musicItem: MusicItem) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(Color.Red)
        ) {
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
                    modifier = Modifier.padding(bottom = 10.dp)
                )
//                Image(painter = painterResource(id = R.drawable.splash_girl), contentScale = null, modifier = Modifier.size(50.dp))
                Image(
                    painter = painterResource(id = R.drawable.splash_girl),
                    modifier = Modifier
                        .width(200.dp)
                        .height(60.dp),
                    contentDescription = null
                )
                AndroidView(
                    modifier = Modifier
                        .padding(bottom = 85.dp, top = 15.dp)
                        .background(Color.Transparent),
                    factory = {
                        LyricViewX(requireContext()).apply {
                            lyricsView = this
                            lyricsView.loadLyricByUrl(lyricUrl = musicItem.fileText.fileUrl)
                            lyricsView.setNormalTextSize(20F)
                            lyricsView.setCurrentTextSize(25F)
                            lyricsView.setDraggable(true, object : OnPlayClickListener {
                                override fun onPlayClick(time: Long): Boolean {
                                    lyricsView.updateTime(position)
                                    return true
                                }
                            })
                            fun lyricUpdateLoop() {
                                lyricsView.updateTime(position)
                                if (!isPaused) {
                                    position += 200L
                                }
                                lyricsView.postDelayed({ lyricUpdateLoop() }, 200)
                            }
                            lyricsView.postDelayed({
                                lyricUpdateLoop()
                            }, 1000)
                        }
                    })
            }
            AndroidView(modifier = Modifier
                .background(Color(R.color.dark_blue))
                .align(Alignment.BottomStart), factory = {
                PlayerControlView(requireContext()).apply {
                    playerView = this
                    playerView.player = exoPlayer
                    playerView.showTimeoutMs = 0
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer.release()
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
            .size(height = 260.dp, width = 180.dp),
        contentDescription = null
    )
}

@Composable
fun GifView(modifier: Modifier) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(ImageDecoderDecoder.Factory())
        }
        .build()
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context).data(data = R.drawable.audio).build(),
            imageLoader = imageLoader
        ),
        contentDescription = null
    )
}

@Preview
@Composable
fun MusicDialogPreview() {

}