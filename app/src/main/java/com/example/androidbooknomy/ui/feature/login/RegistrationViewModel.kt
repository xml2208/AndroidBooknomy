package com.example.androidbooknomy.ui.feature.login

import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.data.storage.Prefs
import com.example.androidbooknomy.network.ApiClient
import com.example.androidbooknomy.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val apiClient: ApiClient,
    private val prefs: Prefs
) :
    BaseViewModel<RegistrationContract.State, RegistrationContract.Event, RegistrationContract.Effect>() {

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
                setEffect { RegistrationContract.Effect.Navigation.Back }
            }
            RegistrationContract.Event.GoButtonClicked -> {
                setEffect { RegistrationContract.Effect.Navigation.MoveToApp }
            }
            RegistrationContract.Event.RegisterButtonClicked -> {
                setEffect { RegistrationContract.Effect.SendCodeToPhone }
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