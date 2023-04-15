package com.example.androidbooknomy.ui.feature.main.main_app.books

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.data.storage.Prefs
import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.model.BooksModel
import com.example.androidbooknomy.ui.base.BaseViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.home.MainAppRepository
import com.example.androidbooknomy.ui.feature.main.main_app.home.payment.PaymentScreenFragment
import com.example.androidbooknomy.utils.extension.openPaymentFragment
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class BooksScreenViewModel(
    private val prefs: Prefs,
    private val booksRepository: MainAppRepository,
    private val router: Router
    ) :
    BaseViewModel<BooksScreenContract.BooksScreenState, BooksScreenContract.Event>() {

    private val responseBooks = MutableStateFlow<BooksModel?>(null)

    init {
        viewModelScope.launch {
            Log.d("xml2208", "booksScreen: response: ${responseBooks.value}")
            getAllBooks()
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
                BooksScreenContract.Event.OnAudioBooksClicked -> {}
                BooksScreenContract.Event.OnIntensiveBooksClicked -> {}
                else -> {}
            }
        }
    }

    fun onBookItemClicked(bookModel: BookModel) = openPaymentFragment(PaymentScreenFragment.newInstance(bookModel), prefs, router)
    private suspend fun getAllBooks() {
        try {
            responseBooks.value = booksRepository.getAllBooks()
            setState {
                copy(
                    isLoading = false,
                    bookList = responseBooks.value?.booksList ?: emptyList()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}