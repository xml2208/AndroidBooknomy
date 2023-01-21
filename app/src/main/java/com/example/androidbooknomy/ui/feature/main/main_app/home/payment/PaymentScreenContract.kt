package com.example.androidbooknomy.ui.feature.main.main_app.home.payment

import com.example.androidbooknomy.ui.base.CoreEffect
import com.example.androidbooknomy.ui.base.CoreEvent
import com.example.androidbooknomy.ui.base.CoreState

sealed class PaymentScreenContract {

    sealed class Event : CoreEvent {
        object OnBuyClicked : Event()
    }

    data class State(
        val title: String,
        val price: String,
        val salePrice: String,
        val salePercent: String,
        val economyPrice: String
    ) : CoreState

    sealed class Effect : CoreEffect {
        sealed class Navigation : Effect() {
            object MoveToPaymentTypeSelectionScreen: Navigation()
        }
    }
}