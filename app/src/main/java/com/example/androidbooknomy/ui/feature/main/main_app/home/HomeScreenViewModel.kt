package com.example.androidbooknomy.ui.feature.main.main_app.home

import  android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.cicirone.Screens
import com.example.androidbooknomy.model.BookModel
import com.example.androidbooknomy.model.BooksModel
import com.example.androidbooknomy.ui.base.BaseViewModel
import com.example.androidbooknomy.ui.feature.main.main_app.home.payment.PaymentScreenFragment
import com.github.terrakok.cicerone.Router
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val booksRepo: MainAppRepository,
    private val router: Router,
) : BaseViewModel<HomeScreenContract.State, HomeScreenContract.Event>() {

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
            bookItem = null,
            errorMessage = null
        )

    override fun handleEvents(event: HomeScreenContract.Event) {
        for (i in responseBooks.value!!.booksList) {
            when (event) {
                HomeScreenContract.Event.SeeNewsClicked -> {
                    router.navigateTo(Screens.newsFragment())
                }
                HomeScreenContract.Event.SeeAllBooksClicked -> {
                    router.navigateTo(Screens.mainFragmentWithBooksSelected())
                }
                HomeScreenContract.Event.SeeAudioCoursesClicked -> { }
                else -> {}
            }

        }
    }

    fun bookItemClicked(bookModel: BookModel) {
        router.navigateTo(Screens.paymentFragment(PaymentScreenFragment.newInstance(bookModel)))
    }

    private suspend fun getAllBooks() {
        try {
            responseBooks.value = booksRepo.getAllBooks()
            for (i in responseBooks.value!!.booksList) {
                setState {
                    copy(booksList = responseBooks.value?.booksList ?: emptyList(), bookItem = i)
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        setState { copy(booksList = responseBooks.value?.booksList ?: emptyList()) }
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
                Log.d("xml2208", "onComplete: Completed")
            }

            override fun onNext(t: BooksModel) {
                setState { copy(booksList = t.booksList) }
            }
        }
    }
}
