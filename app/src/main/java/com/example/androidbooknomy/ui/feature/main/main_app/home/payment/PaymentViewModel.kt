package com.example.androidbooknomy.ui.feature.main.main_app.home.payment

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.model.BooksModel
import com.example.androidbooknomy.network.ApiClient
import com.example.androidbooknomy.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(private val apiClient: ApiClient) :
    BaseViewModel<PaymentScreenContract.State, PaymentScreenContract.Event, PaymentScreenContract.Effect>() {

    private val bookListResponse = MutableStateFlow<BooksModel?>(null)

    init {
        viewModelScope.launch {
            getAllBooks()
            Log.d("xml2208", "paymentViewModel: ${bookListResponse.value}")
        }
    }

    override fun handleEvents(event: PaymentScreenContract.Event) {
        when (event) {
            PaymentScreenContract.Event.OnBuyClicked -> setEffect {
                PaymentScreenContract.Effect.Navigation.MoveToPaymentTypeSelectionScreen
            }
        }
    }

    override fun setInitialState(): PaymentScreenContract.State =
        PaymentScreenContract.State(
            title = "",
            price = "",
            salePrice = "",
            salePercent = "",
            economyPrice = ""
        )

    private suspend fun getAllBooks() {
        bookListResponse.value = apiClient.getBooks()
        for (i in bookListResponse.value!!.booksList) {
            setState {
                copy(
                    title = i.title,
                    price = i.price,
                    salePrice = i.salePrice,
                    salePercent = i.salePercent,
                    economyPrice = i.economyPrice
                )
            }
        }
    }
}