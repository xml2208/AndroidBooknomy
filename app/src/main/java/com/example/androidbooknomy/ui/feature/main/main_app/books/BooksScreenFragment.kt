package com.example.androidbooknomy.ui.feature.main.main_app.books

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.ui.base.BaseViewModel
import com.example.androidbooknomy.ui.base.ComposeFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksScreenFragment :
    ComposeFragment<BooksScreenContract.BooksScreenState, BooksScreenContract.Event, BooksScreenContract.Effect>() {

    private val viewModel by viewModel<BooksScreenViewModel>()

    override fun retrieveViewModel(): BaseViewModel<BooksScreenContract.BooksScreenState, BooksScreenContract.Event, BooksScreenContract.Effect> =
        getViewModel()

    @Composable
    override fun FragmentContent() {
        BooksScreen(
            state = viewModel.viewState.value,
            effect = viewModel.effect,
            onEventSent = { viewModel.setEvent(it) },
            onNavigationRequested = {
                when (it) {
                    BooksScreenContract.Effect.Navigation.OpenAudioBooksScreen -> { Toast.makeText(requireContext(), "Open audio books screen", Toast.LENGTH_SHORT).show()}
                    BooksScreenContract.Effect.Navigation.MoveToPaymentScreen -> {
                        Toast.makeText(requireContext(), "Open payment screen", Toast.LENGTH_SHORT).show()
                        Log.d("xml", "list: ${viewModel.viewState.value.bookList.size}")
                    }
                    BooksScreenContract.Effect.Navigation.OpenIntensiveBooksScreen -> {Toast.makeText(requireContext(), "Open intensive book screen", Toast.LENGTH_SHORT).show()}
                }
            }
        )
    }
}

@Composable
fun BooksScreen(
    state: BooksScreenContract.BooksScreenState,
    effect: Flow<BooksScreenContract.Effect>,
    onEventSent: (BooksScreenContract.Event) -> Unit,
    onNavigationRequested: (BooksScreenContract.Effect.Navigation) -> Unit
) {
    LaunchedEffect(Unit) {
        effect.onEach {
            when (it) {
                BooksScreenContract.Effect.Navigation.OpenAudioBooksScreen -> {
                    onNavigationRequested(BooksScreenContract.Effect.Navigation.OpenAudioBooksScreen)
                }
                BooksScreenContract.Effect.Navigation.MoveToPaymentScreen -> {
                    onNavigationRequested(BooksScreenContract.Effect.Navigation.MoveToPaymentScreen)
                }
                BooksScreenContract.Effect.Navigation.OpenIntensiveBooksScreen -> {
                    onNavigationRequested(BooksScreenContract.Effect.Navigation.OpenIntensiveBooksScreen)
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
//                    .padding(30.dp)
            )
    }

        BooksScreenSecondBox(
            modifier = Modifier
                .weight(5f)
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(Color.White)
                .padding(15.dp),
            state = state,
            onEventSent = onEventSent
        )
    }
}

@Composable
fun BooksScreenSecondBox(
    modifier: Modifier,
    state: BooksScreenContract.BooksScreenState,
    onEventSent: (event: BooksScreenContract.Event) -> Unit
) {
    Column(modifier = modifier) {
        Row {
            TextButton(
                onClick = { onEventSent(BooksScreenContract.Event.OnIntensiveBooksClicked) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Intensiv kitoblar")
            }
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = { onEventSent(BooksScreenContract.Event.OnIntensiveBooksClicked) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Audio kitoblar")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(state.bookList) {
                BookItem(
                    modifier = Modifier
                        .padding(10.dp),
                    bookModel = it,
                    onEventSent = onEventSent
                )
            }
        }
        Spacer(Modifier.width(15.dp))
    }
}

@Composable
fun BookItem(
    modifier: Modifier,
    bookModel: BookModel,
    onEventSent: (event: BooksScreenContract.Event) -> Unit
) {
    Column(modifier = modifier.clickable { onEventSent(BooksScreenContract.Event.OnBookItemClicked) }) {
        Image(
            painter = painterResource(id = R.drawable.bg_audio),
            modifier = Modifier
                .size(width = 120.dp, height = 200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,

            )
        Text(text = bookModel.title, fontWeight = FontWeight.Bold)
    }
}

@Preview
@Composable
fun BookItemPrev() {
    BookItem(
        bookModel = BookModel(
            "asfdsdf",
            "sakjfgkhf",
            "289890",
            "34967",
            "30",
            "87324",
            "790"
        ),
        modifier = Modifier
    ) {}
}
