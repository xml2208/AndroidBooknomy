package com.example.androidbooknomy.ui.feature.main.main_app.books

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.model.BooksModel
import com.example.androidbooknomy.ui.base.BaseViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.home.MainAppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BooksScreenViewModel(private val booksRepository: MainAppRepository) :
    BaseViewModel<BooksScreenContract.BooksScreenState, BooksScreenContract.Event, BooksScreenContract.Effect>() {

    private val responseBooks = MutableStateFlow<BooksModel?>(null)

    init {
        try {
            Log.d("xml2208", "booksScreen: respone: ${responseBooks.value}")
            getAllBooks()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setInitialState(): BooksScreenContract.BooksScreenState =
        BooksScreenContract.BooksScreenState(
            isLoading = false,
            bookList = emptyList(),
            errorMessage = null
        )

    override fun handleEvents(event: BooksScreenContract.Event) {
        for (i in responseBooks.value?.booksList ?: emptyList()) {
            when (event) {
                BooksScreenContract.Event.OnBookItemClicked(i) -> setEffect {
                    BooksScreenContract.Effect.Navigation.MoveToPaymentScreen
                }
                BooksScreenContract.Event.OnAudioBooksClicked -> setEffect {
                    BooksScreenContract.Effect.Navigation.OpenAudioBooksScreen
                }
                BooksScreenContract.Event.OnIntensiveBooksClicked -> setEffect {
                    BooksScreenContract.Effect.Navigation.OpenIntensiveBooksScreen
                }
                else -> {}
            }
        }
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            responseBooks.value = booksRepository.getAllBooks()
            setState {
                copy(
                    isLoading = false,
                    bookList = responseBooks.value?.booksList ?: emptyList()
                )
            }
        }
    }
}