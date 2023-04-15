package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooknomy.R
import com.example.androidbooknomy.cicirone.Screens
import com.example.androidbooknomy.model.FilmModel
import com.example.androidbooknomy.model.SingleFilm
import com.example.androidbooknomy.model.SingleImage
import com.example.androidbooknomy.utils.extension.handleBackPressedEvent
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

const val FILM_ID = "film_id"

class AboutMovieFragment : Fragment() {

    private lateinit var filmModel: FilmModel
    private val router by inject<Router>()

    companion object {
        fun newInstance(filmModel: FilmModel): AboutMovieFragment {
            val args = Bundle().apply {
                putParcelable(FILM_ID, filmModel)
            }
            return AboutMovieFragment().apply {
                arguments = args
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        handleBackPressedEvent { router.navigateTo(Screens.mainFragmentWithEntertainmentSelected()) }
        filmModel = arguments?.getParcelable(FILM_ID)!!

        setContent {
            AboutMovieScreen(filmModel) {
                router.navigateTo(Screens.playMovieFragment(filmModel))
            }
        }
    }

    @Composable
    fun AboutMovieScreen(
        filmModel: FilmModel,
        playMovie: () -> Unit
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                MovieBlurredImage(
                    url = filmModel.image.imageUrl,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                )
                Column(
                    Modifier
                        .weight(2f)
                        .background(Color.Black)
                        .padding(top = 70.dp, start = 20.dp)
                        .fillMaxHeight()
                ) {
                    Text(
                        text = filmModel.title,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = filmModel.duration, color = Color.Gray)
                    Text(
                        text = filmModel.movieLanguage,
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Divider(Modifier.background(Color.Gray))
                    Text(
                        text = stringResource(R.string.about_syujed),
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Text(text = encodeString(filmModel.content), color = Color.White)
                    }
                }
            }
            MovieImage(
                url = filmModel.image.imageUrl,
                modifier = Modifier
                    .padding(start = 0.dp, top = 50.dp)
                    .size(width = 180.dp, height = 200.dp)
                    .clip(RoundedCornerShape(30.dp))
            )
            Icon(
                painter = painterResource(id = R.drawable.play_ic),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 190.dp, end = 30.dp)
                    .align(Alignment.TopEnd)
                    .background(Color.White)
                    .size(55.dp)
                    .clickable { playMovie() }
            )
        }
    }

    @Composable
    fun MovieBlurredImage(url: String, modifier: Modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .placeholder(R.drawable.netflix_img)
                .transformations(listOf(BlurTransformation()))
                .build(),
            modifier = modifier,
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }

    @Composable
    fun MovieImage(url: String, modifier: Modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .placeholder(R.drawable.bg_media_player)
                .build(),
            modifier = modifier,
            contentDescription = null
        )
    }

    private fun encodeString(text: String): String {
        return HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
    }

    @Preview(showSystemUi = true)
    @Composable
    fun AboutMovieScreenPrev() {
        AboutMovieScreen(
            FilmModel(
                1,
                title = "lion",
                duration = "1:12:12",
                movieLanguage = "Rus",
                "sd",
                films = SingleFilm(""),
                image = SingleImage("https://appbooknomy.uz/uploads/Bc/3fts26hYTxDRI88gNo7f52ZAcEp1Ax_low.jpg")
            )
        ) {}
    }

    @Preview(showSystemUi = true)
    @Composable
    fun SimpleUI() {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                        .background(Color.Gray)
                ) {
                    Text(text = "tyu")
                }
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxSize()
                        .background(Color.Green)
                ) {
                    Text(text = "sfsfg")
                }
            }
            Image(
                painter = painterResource(id = R.drawable.bg_media_player),
                modifier = Modifier.size(90.dp),
                contentDescription = null
            )
        }
    }
}                   