package com.example.androidbooknomy.ui.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.androidbooknomy.R
import com.example.androidbooknomy.cicirone.Screens
import com.example.androidbooknomy.utils.extension.handleBackPressedEvent
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val router: Router by inject()
    private val navigator = AppNavigator(this, R.id.main_container, supportFragmentManager)
    private val navHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("xml", "onCreate: Main Activity created")
        super.onCreate(savedInstanceState)
        handleBackPressedEvent { router.exit() }
        setContentView(R.layout.activity_main)

        router.navigateTo(Screens.mainFragment())
    }

    override fun onResume() {
        super.onResume()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
    }
}