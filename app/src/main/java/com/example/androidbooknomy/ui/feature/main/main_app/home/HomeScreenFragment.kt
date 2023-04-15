package com.example.androidbooknomy.ui.feature.main.main_app.home

import android.util.Log
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
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.ui.base.ComposeFragment
import com.example.androidbooknomy.ui.feature.main.AppTopScreen
import com.example.androidbooknomy.ui.feature.main.main_app.books.BookItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.androidbooknomy.utils.extension.handleBackPressedEvent
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class HomeScreenFragment :
    ComposeFragment<HomeScreenContract.State, HomeScreenContract.Event>() {

    private val viewModel by viewModel<HomeScreenViewModel>()
    private val router by inject<Router>()
    private lateinit var bookModel: BookModel

    @Composable
    override fun FragmentContent() {
        HomeScreen(
            state = viewModel.viewState.value,
            onEventSent = { viewModel.setEvent(it) }
        )
    }

    override fun onBackPressed() {
        handleBackPressedEvent {
            router.exit()
        }
    }

    @Composable
    fun HomeScreen(
        state: HomeScreenContract.State,
        onEventSent: (event: HomeScreenContract.Event) -> Unit,
    ) {
        AppTopScreen(background = R.drawable.bg_splash) {
            HomeScreenSecondBox(
                onEventSent = onEventSent,
                state = state,
                modifier = Modifier
                    .fillMaxSize()
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
                            BookItem(
                                bookModel = it,
                                item = { ButtonOfBookItem() },
                                modifier = Modifier
                                    .clickable {
                                        bookModel = it
//                                        (activity as MainActivity).btn.visibility = View.GONE
                                        viewModel.bookItemClicked(bookModel)
                                     //   onEventSent(HomeScreenContract.Event.SeeBookClicked(bookModel))
                                    }
                                    .padding(6.dp),
                                imageHeight = 140.dp,
                                imageWidth = 80.dp
                            )
                        }
                    }
                }

                Row {
                    Text(text = "Audio kurslar")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.barchasi),
                        color = Color.Red,
                        modifier = Modifier.clickable { onEventSent(HomeScreenContract.Event.SeeAllBooksClicked) }
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonOfBookItem() {
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Text(text = "Ko'rish")
    }
}