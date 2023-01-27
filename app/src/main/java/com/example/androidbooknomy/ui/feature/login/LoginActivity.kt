package com.example.androidbooknomy.ui.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.androidbooknomy.R
import com.example.androidbooknomy.data.storage.Prefs
import com.example.androidbooknomy.ui.feature.main.MainActivity
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val prefs by inject<Prefs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        if(prefs.isLoggedIn) { startActivity(MainActivity.getStartIntent(this@LoginActivity)) }
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            initAuthScreen()
        }
    }

    private fun initAuthScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.login_activity_fragment, EntryFragment())
            .commitNow()
    }

    companion object {
        fun getStartIntent(context: Context) =
            Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
    }
}