package com.example.androidbooknomy.ui.feature.main.main_app.home

import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.ui.base.CoreEffect
import com.example.androidbooknomy.ui.base.CoreEvent
import com.example.androidbooknomy.ui.base.CoreState

class HomeScreenContract {

    sealed class Event: CoreEvent {
        object SeeNewsClicked: Event()
        object SeeAllBooksClicked: Event()
        object SeeAudioCoursesClicked: Event()
        object SeeBookClicked: Event()
    }

    data class State(
        val booksList: List<BookModel> = emptyList(),
        val errorMessage: String? = null
    ): CoreState

    sealed class Effect: CoreEffect {
        sealed class Navigation: Effect() {
            object MoveToBooksScreen: Navigation()
            object MoveToAllNewsScreen: Navigation()
            object MoveToBookPaymentScreen: Navigation()
        }
    }
}