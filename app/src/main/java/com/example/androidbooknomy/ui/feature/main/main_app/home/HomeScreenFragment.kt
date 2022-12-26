package com.example.androidbooknomy.ui.feature.main.main_app.home

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.ui.base.BaseViewModel
import com.example.androidbooknomy.ui.base.ComposeFragment
import com.example.androidbooknomy.ui.feature.main.MainActivity
import com.example.androidbooknomy.ui.feature.main.main_app.home.news.NewsFragment
import com.example.androidbooknomy.utils.extension.replaceFragment
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreenFragment() :
    ComposeFragment<HomeScreenContract.State, HomeScreenContract.Event, HomeScreenContract.Effect>() {

    private val viewModel by viewModel<HomeScreenViewModel>()

    override fun retrieveViewModel(): BaseViewModel<HomeScreenContract.State, HomeScreenContract.Event, HomeScreenContract.Effect> =
        getViewModel()

    @Composable
    override fun FragmentContent() {
        HomeScreen(
            state = viewModel.viewState.value,
            effect = viewModel.effect,
            onEventSent = { viewModel.setEvent(it) },
            onNavigationRequested = {
                when (it) {
                    HomeScreenContract.Effect.Navigation.MoveToAllNewsScreen -> {
                        (activity as MainActivity).replaceFragment(
                            NewsFragment(),
                            R.id.main_fragment_container
                        )
                        Toast.makeText(requireContext(), "news screen", Toast.LENGTH_SHORT).show()
                    }
                    HomeScreenContract.Effect.Navigation.MoveToBookPaymentScreen -> {
                        Toast.makeText(requireContext(), "books screen", Toast.LENGTH_SHORT).show()
                    }
                    HomeScreenContract.Effect.Navigation.MoveToBooksScreen -> {
                        Toast.makeText(requireContext(), "paymnet screen", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        )
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            try {
                Log.d("xml", "FragmentContent: ${viewModel.viewState.value.booksList}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @SuppressLint("NotConstructor")
    @Composable
    fun HomeScreen(
        state: HomeScreenContract.State,
        effect: Flow<HomeScreenContract.Effect>,
        onEventSent: (event: HomeScreenContract.Event) -> Unit,
        onNavigationRequested: (HomeScreenContract.Effect.Navigation) -> Unit
    ) {

        LaunchedEffect(Unit) {
            effect.onEach {
                when (it) {
                    HomeScreenContract.Effect.Navigation.MoveToBooksScreen -> {
                        onNavigationRequested(HomeScreenContract.Effect.Navigation.MoveToBooksScreen)
                    }
                    HomeScreenContract.Effect.Navigation.MoveToAllNewsScreen -> {
                        onNavigationRequested(HomeScreenContract.Effect.Navigation.MoveToAllNewsScreen)
                    }
                    HomeScreenContract.Effect.Navigation.MoveToBookPaymentScreen -> {
                        onNavigationRequested(HomeScreenContract.Effect.Navigation.MoveToBookPaymentScreen)
                    }
                }
            }.collect()
        }

        Column(Modifier.background(Color.Black)) {
            Box(modifier = Modifier.weight(2f)) {
                Image(
                    painter = painterResource(id = R.drawable.bg_splash),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Image(
                    painter = painterResource(id = R.drawable.top_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(30.dp)
                )
            }

            HomeScreenSecondBox(
                onEventSent = onEventSent,
                state = state,
                modifier = Modifier
                    .weight(5f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    .background(Color.White)
                    .padding(15.dp)
            )
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun HomeScreenSecondBox(
        modifier: Modifier,
        state: HomeScreenContract.State,
        onEventSent: (event: HomeScreenContract.Event) -> Unit
    ) {

        val pagerState = rememberPagerState()

        Box(
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = stringResource(R.string.news_text),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.barchasi),
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        fontSize = 15.sp,
                        modifier = Modifier.clickable { onEventSent(HomeScreenContract.Event.SeeNewsClicked) }
                    )
                }

                HorizontalPager(count = 2) {
                    Image(
                        painter = painterResource(id = R.drawable.bg_audio),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .clip(RoundedCornerShape(17.dp))
                            .size(120.dp)
                    )
                }

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    activeColor = Color.Gray,
                    inactiveColor = Color.LightGray,
                    modifier = Modifier.padding(10.dp)
                )

                //booknomy books
                Column {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = stringResource(R.string.booknomy_books),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(R.string.barchasi),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                Log.d("books", "Books: ${state.booksList}")
                                onEventSent(HomeScreenContract.Event.SeeAllBooksClicked)
                            },

                            color = Color.Red,
                        )

                    }

                    LazyRow {
                        items(state.booksList) {
                            BookItem(bookModel = it, onEventSent)
                        }
                    }
                }

                Row {
                    Text(text = "Audio kurslar")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.barchasi),
                        color = Color.Red,
                        modifier = Modifier.clickable { onEventSent(HomeScreenContract.Event.SeeAllBooksClicked) })
                }
            }
        }
    }
}
@Composable
fun BookItem(bookModel: BookModel, onEventSent: (event: HomeScreenContract.Event) -> Unit) {
    Column(modifier = Modifier.padding(6.dp)) {
        Image(
            painter = painterResource(id = R.drawable.bg_audio),
            modifier = Modifier
                .size(width = 80.dp, height = 140.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,

            )
        Text(text = bookModel.title, fontWeight = FontWeight.Bold)
        Button(onClick = { onEventSent(HomeScreenContract.Event.SeeBookClicked) }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black, contentColor = Color.White)) {
            Text(text = "Ko'rish")
        }
    }
}
