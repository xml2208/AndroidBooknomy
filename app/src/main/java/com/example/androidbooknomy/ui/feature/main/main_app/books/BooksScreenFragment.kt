package com.example.androidbooknomy.ui.feature.main.main_app.books

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.androidbooknomy.R
import com.example.androidbooknomy.data.storage.Prefs
import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.ui.base.ComposeFragment
import com.example.androidbooknomy.ui.feature.main.AppTopScreen
import com.example.androidbooknomy.ui.feature.main.main_app.home.payment.PaymentScreenFragment
import com.example.androidbooknomy.utils.extension.openPaymentFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BooksScreenFragment :
    ComposeFragment<BooksScreenContract.BooksScreenState, BooksScreenContract.Event, BooksScreenContract.Effect>() {

    private val viewModel by viewModel<BooksScreenViewModel>()
    private val prefs by inject<Prefs>()
    private lateinit var bookModel: BookModel

    override fun retrieveViewModel() = getViewModel<BooksScreenViewModel>()

    @Composable
    override fun FragmentContent() {
        BooksScreen(
            state = viewModel.viewState.value,
            onEventSent = { viewModel.setEvent(it) }
        )
    }

    override fun handleEffect(effect: BooksScreenContract.Effect) {
        super.handleEffect(effect)
        when (effect) {
            BooksScreenContract.Effect.Navigation.OpenAudioBooksScreen -> {
                Toast.makeText(requireContext(), "Open audio books screen", Toast.LENGTH_SHORT).show()
                Log.d("xml", "BooksScreen: open audio screen triggered")
            }
            BooksScreenContract.Effect.Navigation.MoveToPaymentScreen -> {
                openPaymentFragment(PaymentScreenFragment.newInstance(bookModel), prefs)
                Log.d("xml", "list: ${viewModel.viewState.value.bookList.size}")
            }
            BooksScreenContract.Effect.Navigation.OpenIntensiveBooksScreen -> {
                Toast.makeText(requireContext(), "Open intensive book screen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Composable
    fun BooksScreen(
        state: BooksScreenContract.BooksScreenState,
        onEventSent: (BooksScreenContract.Event) -> Unit,
    ) {
        AppTopScreen(column = {
            BooksScreenSecondBox(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                    .background(Color.White)
                    .padding(15.dp),
                state = state,
                onEventSent = onEventSent
            )
        }, background = R.drawable.bg_splash)
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
                            .padding(6.dp)
                            .clickable {
                                bookModel = it
                                onEventSent(BooksScreenContract.Event.OnBookItemClicked(bookModel))
                            },
                        bookModel = it,
                        imageWidth = 120.dp, imageHeight = 200.dp
                    )
                }
            }
            Spacer(Modifier.width(15.dp))
        }
    }
}

@Composable
fun BookItem(
    modifier: Modifier,
    bookModel: BookModel,
    imageWidth: Dp,
    imageHeight: Dp,
    item: @Composable () -> Unit = {}
) {
    Column(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).placeholder(R.drawable.bg_audio).data(bookModel.photo.photoUrl).build(),
            modifier = Modifier
                .size(width = imageWidth, height = imageHeight)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Text(text = bookModel.title, fontWeight = FontWeight.Bold)
        item()
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
            "790",
        ),
        modifier = Modifier,
        imageHeight = 200.dp,
        imageWidth = 120.dp
    )
}

