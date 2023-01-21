package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

const val VIDEO_URL_KEY = "video_url"

class PlayMovie : Fragment() {

    private lateinit var videUrl: String

    companion object {
        fun newInstance(videoUrl: String): PlayMovie {
            val args = Bundle().apply {
                putString(VIDEO_URL_KEY, videoUrl)
            }
            return PlayMovie().apply {
                arguments = args
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {

        videUrl = arguments?.getString(VIDEO_URL_KEY).toString()

        setContent {
            Toast.makeText(requireContext(), "Please, wait for requesting...", Toast.LENGTH_LONG)
                .show()
            PlayerView(videUrl)
        }
    }
}
    @Composable
    fun PlayerView(mediaUrl: String) {
        val context = LocalContext.current
        val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
        val exoPlayer = remember {
            ExoPlayer.Builder(context)
                .build()
                .also { exoPlayer ->
                    val mediaItem = MediaItem.Builder()
                        .setUri(mediaUrl)
                        .build()
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                }
        }

        AndroidView(
            factory = {
                StyledPlayerView(context).apply {
                    player = exoPlayer
                }
            },
        )

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        exoPlayer.play()
                    }
                    Lifecycle.Event.ON_STOP -> {
                        exoPlayer.pause()
                    }
                    Lifecycle.Event.ON_DESTROY -> {
                        exoPlayer.release()
                    }
                    else -> Unit
                }
            }

            // Add the observer to the lifecycle
            lifecycleOwner.lifecycle.addObserver(observer)

            // When the effect leaves the Composition, remove the observer
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    }