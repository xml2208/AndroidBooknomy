package com.example.androidbooknomy.ui.feature.main.main_app.entertainment.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.FilmModel
import com.example.androidbooknomy.model.SingleFilm
import com.example.androidbooknomy.model.SingleImage
import com.example.androidbooknomy.utils.extension.openFragmentInActivity

const val FILM_ID = "film_id"

class AboutMovieFragment : Fragment() {

    private lateinit var filmModel: FilmModel

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

        filmModel = arguments?.getParcelable(FILM_ID)!!

        setContent {
            AboutMovieScreen(filmModel) {
                openFragmentInActivity(PlayMovie.newInstance(filmModel.films.filmUrl))
            }
        }
    }

    @Composable
    fun AboutMovieScreen(
        filmModel: FilmModel,
        playMovie: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxSize()) {
                MovieBlurredImage(
                    url = filmModel.image.imageUrl,
                    modifier = Modifier.fillMaxSize()
                )
                Icon(
                    painter = painterResource(id = R.drawable.play_ic),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(45.dp)
                        .clickable { playMovie() }
                )
//                    MovieImage(
//                        url = filmModel.image.imageUrl,
//                        modifier = Modifier.aspectRatio(3/5f).clip(RoundedCornerShape(10.dp))
//                    )
            }
            Column(
                Modifier
                    .weight(2f)
                    .background(Color.Black)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()
                    .blur(30.dp)
            ) {

                Text(text = filmModel.title, color = Color.White, fontSize = 20.sp)
                Text(text = filmModel.duration, color = Color.Gray)
                Text(text = filmModel.movieLanguage, color = Color.White)
                Divider(Modifier.background(Color.Gray))
                Text(text = stringResource(R.string.about_syujed), color = Color.White)
                Text(text = encodeString(filmModel.content), color = Color.White)
            }
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
                .placeholder(R.drawable.netflix_img)
                .build(),
            modifier = modifier,
            contentScale = ContentScale.Crop,
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
        Column(Modifier.fillMaxWidth()) {
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
                Text(text = "tuyutg")

            }
        }
    }
}
