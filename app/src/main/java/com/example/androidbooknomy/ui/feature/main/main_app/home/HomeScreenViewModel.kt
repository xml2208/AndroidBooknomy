package com.example.androidbooknomy.ui.feature.main.main_app.home

import  android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.model.BooksModel
import com.example.androidbooknomy.ui.base.BaseViewModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val booksRepo: MainAppRepository
) : BaseViewModel<HomeScreenContract.State, HomeScreenContract.Event, HomeScreenContract.Effect>() {

    lateinit var disposable: Disposable
    private val responseBooks = MutableStateFlow<BooksModel?>(null)

    init {
        viewModelScope.launch {
            getAllBooks()
            Log.d("xml2208", "vm: ${responseBooks.value}")
        }
    }

    override fun setInitialState(): HomeScreenContract.State =
        HomeScreenContract.State(
            booksList = emptyList(),
            errorMessage = null
        )

    override fun handleEvents(event: HomeScreenContract.Event) {
        when (event) {
            HomeScreenContract.Event.SeeNewsClicked -> setEffect {
                HomeScreenContract.Effect.Navigation.MoveToAllNewsScreen
            }
            HomeScreenContract.Event.SeeAllBooksClicked -> setEffect {
                HomeScreenContract.Effect.Navigation.MoveToBooksScreen
            }
            HomeScreenContract.Event.SeeAudioCoursesClicked -> setEffect {
                HomeScreenContract.Effect.Navigation.MoveToBookPaymentScreen
            }
            HomeScreenContract.Event.SeeBookClicked -> setEffect {
                HomeScreenContract.Effect.Navigation.MoveToBookPaymentScreen
            }
        }
    }

    private suspend fun getAllBooks() {
        responseBooks.value = booksRepo.getAllBooks()
        setState { copy(booksList = responseBooks.value?.booksList ?: emptyList()) }
//        response
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndS)
//            .subscribe(getBooksListObserver())

    }

    private fun getBooksListObserver(): Observer<BooksModel> {
        return object : Observer<BooksModel> {

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onError(e: Throwable) {
                setState { copy(errorMessage = e.message) }
            }

            override fun onComplete() {
                Log.d("homescreenvm", "onComplete: Completed")}

            override fun onNext(t: BooksModel) {
                setState { copy(booksList = t.booksList) }
            }
        }
    }
}
