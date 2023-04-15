package com.example.androidbooknomy.ui.feature.login

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.analytics.AnalyticsUseCase
import com.example.androidbooknomy.analytics.AnalyticsUseCaseImpl
import com.example.androidbooknomy.cicirone.Screens
import com.example.androidbooknomy.data.storage.Prefs
import com.example.androidbooknomy.network.ApiClient
import com.example.androidbooknomy.ui.base.BaseViewModel
import com.github.terrakok.cicerone.Router
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val apiClient: ApiClient,
    private val prefs: Prefs,
    private val router: Router,
    private val analytics: AnalyticsUseCase
) :
    BaseViewModel<RegistrationContract.State, RegistrationContract.Event>() {

    var phoneNumber by mutableStateOf("")

    var code by mutableStateOf("")
        private set

    var showCodeField by mutableStateOf(false)
        private set

    var isCodeValid by mutableStateOf(false)
        private set

    val phoneNumberIsCorrect: Boolean by derivedStateOf {
        phoneNumber.count() == 13 && phoneNumber.first() == '+'
    }

    fun updatePhoneNumber(number: String) {
        phoneNumber = number
    }

    fun updateCode(codeSms: String) {
        code = codeSms
    }


    override fun setInitialState(): RegistrationContract.State = RegistrationContract.State(
        phoneNumber = "",
        code = null,
        errorText = null
    )

    override fun handleEvents(event: RegistrationContract.Event) {
        when (event) {
            RegistrationContract.Event.NavigateUp -> {
                router.exit()
            }
            RegistrationContract.Event.RegisterButtonClicked -> {
                try {
                    sendSmsToPhone()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            RegistrationContract.Event.GoButtonClicked -> {
                try {
                    saveToken(phoneNumber, code)
                    Log.d("xml", "token: ${prefs.token}")
                    Log.d("xml", "token: ${prefs.isLoggedIn}")
                    if (prefs.isLoggedIn) {
                        router.navigateTo(Screens.mainActivity())
                    }
                    analytics.log(AnalyticsUseCaseImpl.LOG_IN_EVENT) { param("isLogged", prefs.isLoggedIn.toString()) }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun sendSmsToPhone() {
        try {
            viewModelScope.launch {
                apiClient.sendSmsToPhone(phoneNumber)
                showCodeField = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun saveToken(num: String, code: String) {
        viewModelScope.launch {
            try {
                prefs.saveToken(apiClient.approveUser(num, code)["token"] as String)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    fun checkCodeIsValid() {
//        try {
//            viewModelScope.launch {
//                _token.value = prefs.getToken
//                Log.d("xml2208", "jsonObject: ${apiClient.approveUser(phoneNumber, code)["token"]}")
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

}