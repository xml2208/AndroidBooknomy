package com.example.androidbooknomy.ui.feature.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.AutofillType
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.androidbooknomy.R
import com.example.androidbooknomy.network.ApiClient
import com.example.androidbooknomy.ui.base.BaseViewModel
import com.example.androidbooknomy.ui.base.ComposeFragment
import com.example.androidbooknomy.ui.feature.main.MainActivity
import com.example.androidbooknomy.utils.extension.replaceFragment
import com.example.androidbooknomy.utils.isPhoneNumberValid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment :
    ComposeFragment<RegistrationContract.State, RegistrationContract.Event, RegistrationContract.Effect>() {

    private val viewModel by viewModel<RegistrationViewModel>()
    private val api by inject<ApiClient>()

    override fun retrieveViewModel(): BaseViewModel<RegistrationContract.State, RegistrationContract.Event, RegistrationContract.Effect> =
        getViewModel<RegistrationViewModel>()

    @Composable
    override fun FragmentContent() {
        RegistrationUi(
            phoneNumber = viewModel.phoneNumber,
            code = viewModel.code,
            phoneNumberIsIncorrect = viewModel.phoneNumberIncorrect,
            onPhoneNumberChanged = viewModel::updatePhoneNumber,
            onCodeChanged = viewModel::updateCode,
            showCodeTextField = viewModel.showCodeField,
            effectFlow = viewModel.effect,
            onEventSent = { viewModel.setEvent(it) },
            onNavigationRequested = {
                when (it) {
                    RegistrationContract.Effect.Navigation.Back -> {
                        Toast.makeText(requireContext(), "back button pressed!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    RegistrationContract.Effect.SendCodeToPhone -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            try {
                                viewModel.sendSmsToPhone()
                            } catch(e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                    RegistrationContract.Effect.Navigation.MoveToApp -> {
                        startActivity(MainActivity.getStartIntent(requireContext()))
                    }
                }
            }
        )
    }

    @Composable
    private fun RegistrationUi(
        phoneNumber: String,
        code: String,
        phoneNumberIsIncorrect: Boolean,
        onPhoneNumberChanged: (String) -> Unit,
        onCodeChanged: (String) -> Unit,
        showCodeTextField: Boolean,
        effectFlow: Flow<RegistrationContract.Effect>?,
        onEventSent: (event: RegistrationContract.Event) -> Unit,
        onNavigationRequested: (RegistrationContract.Effect.Navigation) -> Unit
    ) {

        LaunchedEffect(Unit) {
            effectFlow?.onEach { effect ->
                when (effect) {
                    RegistrationContract.Effect.Navigation.Back -> {
                        onNavigationRequested(RegistrationContract.Effect.Navigation.Back)
                    }
                    RegistrationContract.Effect.SendCodeToPhone -> {
                        onNavigationRequested(RegistrationContract.Effect.SendCodeToPhone)
                    }
                    RegistrationContract.Effect.Navigation.MoveToApp -> {
                        onNavigationRequested(RegistrationContract.Effect.Navigation.MoveToApp)
                    }
                }
            }?.collect()
        }

        Column {
            Box(Modifier.weight(1f)) {
                Image(
                    painter = painterResource(id = R.drawable.bg_splash),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            onEventSent(
                                RegistrationContract
                                    .Event.NavigateUp
                            )
                        },
                    tint = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.top_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(40.dp)
                        .align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {

                    Text(
                        text = stringResource(id = R.string.enter_phone),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(10.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Column(modifier = Modifier.padding(20.dp)) {

                        TextField(
                            onValueChange = onPhoneNumberChanged,
                            value = phoneNumber,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                            placeholder = { Text(text = "+998 12 345 67 89") },
                            maxLines = 1
                        )
                        if (showCodeTextField) {
                            Row {
                                TextField(
                                    onValueChange = onCodeChanged,
                                    value = code,
                                    modifier = Modifier
                                        .padding(vertical = 20.dp)
                                        .weight(4f),
                                    placeholder = { Text(text = "code") },
                                    maxLines = 1
                                )
                                TextButton(onClick = {
                                    viewModel.checkCodeIsValid()
                                    if(viewModel.isCodeValid) {
                                        onEventSent(RegistrationContract.Event.GoButtonClicked)
                                    }
                                }, modifier = Modifier.weight(1f)) {
                                    Text(text = "GO")
                                }
                            }

                        }
                    }
                }

                Button(
//                    enabled = phoneNumberIsIncorrect,
                    onClick = {
                        onEventSent(RegistrationContract.Event.RegisterButtonClicked)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(
                        top = 10.dp, bottom = 10.dp
                    )
                ) {
                    Text(text = stringResource(R.string.register_btn), fontSize = 20.sp)
                }

            }
        }
    }
}