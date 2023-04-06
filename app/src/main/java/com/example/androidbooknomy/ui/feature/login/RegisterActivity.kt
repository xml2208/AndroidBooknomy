package com.example.androidbooknomy.ui.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.androidbooknomy.analytics.AnalyticsUseCase
import com.example.androidbooknomy.analytics.AnalyticsUseCaseImpl
import com.example.androidbooknomy.data.storage.Prefs
import com.example.androidbooknomy.ui.feature.main.MainActivity
import com.example.androidbooknomy.utils.extension.toast
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModel<RegistrationViewModel>()
    private val prefs by inject<Prefs>()
    private val analytics by inject<AnalyticsUseCase>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RegistrationUi(
                phoneNumber = viewModel.phoneNumber,
                code = viewModel.code,
                phoneNumberIsCorrect = viewModel.phoneNumberIsCorrect,
                onPhoneNumberChanged = viewModel::updatePhoneNumber,
                onCodeChanged = viewModel::updateCode,
                showCodeTextField = viewModel.showCodeField,
                onEventSent = { viewModel.setEvent(it) })
        }

        observeEffects()
    }

    override fun onResume() {
        super.onResume()
        analytics
    }

    private fun observeEffects() {
        lifecycleScope.launch {
            viewModel.effect.collect {
                handleEffect(it)
            }
        }
    }

    private fun handleEffect(effect: RegistrationContract.Effect) {
        when (effect) {
            RegistrationContract.Effect.Navigation.Back -> {}
            RegistrationContract.Effect.SendCodeToPhone -> {
                lifecycleScope.launch {
                    try {
                        viewModel.sendSmsToPhone()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            RegistrationContract.Effect.Navigation.MoveToApp -> {
                lifecycleScope.launch {
                    try {
                        viewModel.saveToken(viewModel.phoneNumber, viewModel.code)
                        Log.d("xml", "token: ${prefs.token}")
                        toast(prefs.token)
                        analytics.log(AnalyticsUseCaseImpl.LOG_IN_EVENT) {
                            param("isLogged", prefs.isLoggedIn.toString())
                        }
                        if (prefs.isLoggedIn) {
                            startActivity(MainActivity.getStartIntent(this@RegisterActivity))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    @Composable
    private fun RegistrationUi(
        phoneNumber: String,
        code: String,
        phoneNumberIsCorrect: Boolean,
        onPhoneNumberChanged: (String) -> Unit,
        onCodeChanged: (String) -> Unit,
        showCodeTextField: Boolean,
        onEventSent: (event: RegistrationContract.Event) -> Unit,
    ) {
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
                            onEventSent(RegistrationContract.Event.NavigateUp)
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
                            placeholder = { Text(text = stringResource(R.string.phone_num_hint)) },
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
                                    placeholder = { Text(text = stringResource(R.string.code)) },
                                    maxLines = 1
                                )
                                TextButton(
                                    onClick = {
                                        onEventSent(RegistrationContract.Event.GoButtonClicked)
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(Alignment.CenterVertically)
                                ) {
                                    Text(
                                        text = stringResource(R.string.go_btn),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                Button(
                    enabled = phoneNumberIsCorrect,
                    onClick = {
                        onEventSent(RegistrationContract.Event.RegisterButtonClicked)
                    },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Green, contentColor = Color.White
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

    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, RegisterActivity::class.java)
    }
}