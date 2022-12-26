package com.example.androidbooknomy.ui.feature.main.main_app.books

import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.ui.base.CoreEffect
import com.example.androidbooknomy.ui.base.CoreEvent
import com.example.androidbooknomy.ui.base.CoreState

sealed class BooksScreenContract {

    sealed class Event: CoreEvent {
        object OnAudioBooksClicked: Event()
        object OnBookItemClicked: Event()
        object OnIntensiveBooksClicked: Event()
    }

    data class BooksScreenState(
        val isLoading: Boolean = false,
        val bookList: List<BookModel> = emptyList(),
        val errorMessage: String? = null
    ): CoreState

    sealed class Effect: CoreEffect {
        sealed class Navigation: Effect() {
            object OpenAudioBooksScreen: Navigation()
            object MoveToPaymentScreen: Navigation()
            object OpenIntensiveBooksScreen: Navigation()
        }
    }
}