package com.example.androidbooknomy.utils.extension

import android.app.Activity
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.androidbooknomy.R
import com.example.androidbooknomy.analytics.AnalyticsUseCase
import com.example.androidbooknomy.analytics.AnalyticsUseCaseImpl
import com.example.androidbooknomy.cicirone.Screens
import com.example.androidbooknomy.data.storage.Prefs
import com.github.terrakok.cicerone.Router

fun Activity.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun ViewModel.openPaymentFragment(fragment: Fragment, prefs: Prefs, router: Router) {
    if (prefs.isLoggedIn) {
        router.navigateTo(Screens.paymentFragment(fragment))
    } else {
        Toast.makeText(fragment.requireContext(), R.string.unauthorized, Toast.LENGTH_SHORT).show()
        router.navigateTo(Screens.registerActivity())
    }
}

fun Fragment.openBottomNavFragment(analytics: AnalyticsUseCase, fragment: Fragment, containerId: Int) {
    childFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack(null)
        .commit()

    analytics.log(AnalyticsUseCaseImpl.CLICK_EVENT) {
        param("bottom_bar_fragment", "$fragment clicked!")
    }
}

fun FragmentActivity.replaceFragment(
    fragment: Fragment,
    containerId: Int
) {
    supportFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack(null)
        .commit()
}

fun AppCompatActivity.handleBackPressedEvent(onBack: () -> Unit) {
    this.onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBack()
        }
    })
}


fun Fragment.handleBackPressedEvent(onBack: () -> Unit) {
    this.activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBack()
        }
    })
}