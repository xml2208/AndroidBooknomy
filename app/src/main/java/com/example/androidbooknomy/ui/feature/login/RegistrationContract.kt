package com.example.androidbooknomy.ui.feature.login

import com.example.androidbooknomy.ui.base.CoreEffect
import com.example.androidbooknomy.ui.base.CoreEvent
import com.example.androidbooknomy.ui.base.CoreState

class RegistrationContract {

    sealed class Event : CoreEvent {
        object NavigateUp : Event()
        object GoButtonClicked: Event()
        object RegisterButtonClicked : Event()
    }

    data class State(
        val phoneNumber: String = "",
        val errorText: String? = null,
        val code: String?
    ) : CoreState

    sealed class Effect : CoreEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
            object MoveToApp : Navigation()
        }
        object SendCodeToPhone : Navigation()
    }
}