package com.example.androidbooknomy.ui.feature.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.androidbooknomy.R
import com.example.androidbooknomy.cicirone.Screens
import com.example.androidbooknomy.data.storage.Prefs
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class EntryActivity : AppCompatActivity() {

    private val prefs by inject<Prefs>()
    private val router: Router by inject()
    private val navigator: Navigator by inject { parametersOf(this) }
//    private val navigator: Navigator = AppNavigator(this, R.id.main_container)
    private val navHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (prefs.isLoggedIn) {
            router.navigateTo(Screens.mainActivity())
        }
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            EntryUi(background = R.drawable.bg_splash,
                topLogo = R.drawable.top_logo,
                register = { router.navigateTo(Screens.registerActivity()) },
                moveToApp = { router.navigateTo(Screens.mainActivity()) }
            )
        }
    }

    override fun onResume() {
        super.onResume()
        navHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navHolder.removeNavigator()
    }

}