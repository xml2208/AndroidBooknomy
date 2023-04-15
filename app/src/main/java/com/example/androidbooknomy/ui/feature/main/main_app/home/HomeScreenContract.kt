package com.example.androidbooknomy.ui.feature.main.main_app.home

import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.ui.base.CoreEvent
import com.example.androidbooknomy.ui.base.CoreState

class HomeScreenContract {

    sealed class Event: CoreEvent {
        object SeeNewsClicked: Event()
        object SeeAllBooksClicked: Event()
        object SeeAudioCoursesClicked: Event()
    }

    data class State(
        val booksList: List<BookModel> = emptyList(),
        val bookItem: BookModel? = null,
        val errorMessage: String? = null
    ): CoreState

}