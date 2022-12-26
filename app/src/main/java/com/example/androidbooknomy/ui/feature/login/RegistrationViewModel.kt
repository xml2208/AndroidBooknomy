package com.example.androidbooknomy.ui.feature.login

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.androidbooknomy.network.ApiClient
import com.example.androidbooknomy.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class RegistrationViewModel(private val apiClient: ApiClient) :
    BaseViewModel<RegistrationContract.State, RegistrationContract.Event, RegistrationContract.Effect>() {

    var phoneNumber by mutableStateOf("")

    var code by mutableStateOf("")
        private set

    var showCodeField by mutableStateOf(false)
        private set

    var isCodeValid by mutableStateOf(false)
        private set
    val phoneNumberIncorrect: Boolean by derivedStateOf {
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
        viewModelScope.launch {
            apiClient.sendSmsToPhone(phoneNumber)
            showCodeField = true
        }
    }

    fun checkCodeIsValid() {
        try {
            viewModelScope.launch {
//                if (apiClient.approveUser(phoneNumber, code).getBoolean("success")) {
                apiClient.approveUser(phoneNumber, code)
                    isCodeValid = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}