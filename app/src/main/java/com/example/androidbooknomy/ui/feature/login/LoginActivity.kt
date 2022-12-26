package com.example.androidbooknomy.ui.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidbooknomy.R
import com.example.androidbooknomy.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityLoginBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            initAuthScreen()
        }
    }

    private fun initAuthScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, EntryFragment())
            .commitNow()
    }

    companion object {
        fun getStartIntent(context: Context) =
            Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
    }
}