package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.music.FileText
import com.example.androidbooknomy.model.music.Music
import com.example.androidbooknomy.model.music.MusicItem
import com.example.androidbooknomy.model.music.MusicPhoto
import com.example.androidbooknomy.ui.base.BaseViewModel
import com.example.androidbooknomy.ui.base.ComposeFragment
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.film.PlayerView
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ALBUM_ID = "album_id"

class MusicListFragment :
    ComposeFragment<MusicListContract.MusicListState, MusicListContract.MusicListEvent, MusicListContract.MusicListEffect>() {

    private var albumId: Int = 0
    private val viewModel by viewModel<MusicListViewModel>()
    private var selectedMusic: MusicItem =
        MusicItem(0, "", MusicPhoto(""), "", FileText("", ""), Music("", ""))

    companion object {
        fun newInstance(id: Int): MusicListFragment {
            val args = Bundle().apply {
                putInt(ALBUM_ID, id)
            }
            return MusicListFragment().apply {
                arguments = args
            }
        }
    }

    override fun retrieveViewModel(): BaseViewModel<MusicListContract.MusicListState, MusicListContract.MusicListEvent, MusicListContract.MusicListEffect> =
        getViewModel<MusicListViewModel>()

    @Composable
    override fun FragmentContent() {
        albumId = arguments?.getInt(ALBUM_ID) ?: 0
        MusicListScreen(viewModel.viewState.value, onEventSent = { viewModel.setEvent(it) })
    }

    override fun handleEffect(effect: MusicListContract.MusicListEffect) {
        super.handleEffect(effect)
        val bottomSheet = MusicBottomSheetDialog.newInstance(selectedMusic)
        when (effect) {
            MusicListContract.MusicListEffect.PlayMusic -> {
                Toast.makeText(requireContext(), selectedMusic.music.title, Toast.LENGTH_SHORT)
                    .show()
                bottomSheet.show(childFragmentManager, MusicBottomSheetDialog.TAG)
            }
        }
    }

    @Composable
    fun MusicListScreen(
        state: MusicListContract.MusicListState,
        onEventSent: (MusicListContract.MusicListEvent) -> Unit,
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.bg_audio),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            LazyColumn(modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp)) {
                items(state.musicList) { music ->
                    MusicItemUi(
                        musicItem = music,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .clickable {
                                selectedMusic = music
                                onEventSent(MusicListContract.MusicListEvent.OnMusicClicked(music))
                            }
                    )
                }
            }
        }
    }

    @Composable
    fun MusicItemUi(musicItem: MusicItem, modifier: Modifier) {
        Row(modifier = Modifier.padding(7.dp)) {
            NetworkImage(
                url = musicItem.photo.imgUrl,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(65.dp)
            )
            Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
                Divider(Modifier.background(Color.Gray))
                Text(
                    text = musicItem.fileText.title,
                    modifier = Modifier.padding(5.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = musicItem.author,
                    modifier = Modifier.padding(5.dp),
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }

    @Composable
    fun NetworkImage(url: String, modifier: Modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(url)
                .placeholder(R.drawable.bg_splash).build(),
            modifier = modifier,
            contentDescription = null
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun ImagePrev() {
    Box {
        Image(
            painter = painterResource(id = R.drawable.bg_audio),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            contentDescription = null
        )
        Text(text = "i am working")
    }
}