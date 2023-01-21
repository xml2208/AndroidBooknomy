package com.example.androidbooknomy.ui.feature.main.main_app.entertainment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.FilmModel
import com.example.androidbooknomy.model.music.MusicAlbumItem
import com.example.androidbooknomy.ui.feature.main.AppTopScreen
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.film.AboutMovieFragment
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.games.GamesScreen
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list.MusicListFragment
import com.example.androidbooknomy.utils.extension.openFragmentInActivity
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.android.synthetic.main.entertainment_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

enum class TabData {
    KINOLAR, OYINLAR, MUSIQA
}

class EntertainmentFragment : Fragment() {

    private val tabData = listOf(TabData.KINOLAR.name, TabData.OYINLAR.name, TabData.MUSIQA.name)
    private lateinit var film: FilmModel
    private val viewModel by viewModel<EntertainmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {

                AppTopScreen(background = R.drawable.bg_audio) {
                    EntertainmentScreen {
                        when (it) {
                            0 -> {
                                FilmsScreen(
                                    state = viewModel.state.value,
                                    onFilmClicked = { filmModel -> openFragmentInActivity(AboutMovieFragment.newInstance(filmModel)) }
                                )
                            }
                            1 -> { GamesScreen()  }
                            2 -> {
                                MusicAlbumScreen(state = viewModel.state.value, onAlbumSelected = { openFragmentInActivity(MusicListFragment()) })
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun EntertainmentScreen(viewPagerContent: @Composable (Int) -> Unit) {
        val pagerState = rememberPagerState()
        val tabIndex = pagerState.currentPage
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = tabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                indicator = { TabRowDefaults.Divider(thickness = 0.dp) },
                divider = { Divider(Modifier.background(Color.White)) },
                backgroundColor = Color.Transparent
            ) {
                tabData.forEachIndexed { index, text ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        text = { Text(text = text, fontWeight = FontWeight.Bold) },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.Gray,
                    )
                }
            }
            HorizontalPager(state = pagerState, count = tabData.size) { index ->
                Column(Modifier.fillMaxSize()) {
                    viewPagerContent(index)
                }
            }
        }
    }

    @Composable
    private fun FilmsScreen(
        state: EntertainmentScreenState,
        onFilmClicked: (film: FilmModel) -> Unit,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(state.filmsState.filmItems) { filmModel ->
                FilmItem(
                    filmModel = filmModel,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            film = filmModel
                            onFilmClicked(film)
                        })
            }
        }
        Log.d("xml2208", "FilmsScreen: ${viewModel.state.value}")
    }

    @Composable
    private fun FilmItem(modifier: Modifier, filmModel: FilmModel) {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(filmModel.image.imageUrl)
                    .placeholder(R.drawable.netflix_img)
                    .build(),
                modifier = Modifier
                    .aspectRatio(3 / 4f)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Text(
                text = filmModel.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.padding(top = 5.dp)
            )
        }
    }

    @Composable
    private fun MusicAlbumScreen(
        state: EntertainmentScreenState,
        onAlbumSelected: (album: MusicAlbumItem) -> Unit
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(state.musicState.albums) { albumItem ->
                MusicAlbumItem(
                    musicAlbumItem = albumItem,
                    modifier = Modifier
                        .padding(2.dp)
                        .clickable {
//                            albumId = albumItem.id
                            onAlbumSelected(albumItem)
                        }
                )
            }
        }
    }

    @Composable
    private fun MusicAlbumItem(musicAlbumItem: MusicAlbumItem, modifier: Modifier) {
        Column(modifier = modifier) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(musicAlbumItem.files.imageUrl)
                    .placeholder(R.drawable.bg_audio)
                    .build(),
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentDescription = null
            )
            Text(text = musicAlbumItem.title, fontWeight = FontWeight.Bold)
        }
    }
}
