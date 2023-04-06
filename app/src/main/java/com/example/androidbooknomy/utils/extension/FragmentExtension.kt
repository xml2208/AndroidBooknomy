package com.example.androidbooknomy.utils.extension

import android.app.Activity
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.androidbooknomy.R
import com.example.androidbooknomy.analytics.AnalyticsUseCase
import com.example.androidbooknomy.analytics.AnalyticsUseCaseImpl
import com.example.androidbooknomy.data.storage.Prefs
import com.example.androidbooknomy.ui.feature.login.RegisterActivity
import com.example.androidbooknomy.ui.feature.main.MainActivity

fun Activity.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Fragment.openFragmentInActivity(fragment: Fragment) {
    (activity as MainActivity).replaceFragment(fragment, R.id.main_activity_fragment)
}

fun Fragment.openPaymentFragment(fragment: Fragment, prefs: Prefs) {
    if (prefs.isLoggedIn) {
        (activity as MainActivity).replaceFragment(fragment, R.id.main_activity_fragment)
    } else {
        Toast.makeText(context, getString(R.string.unauthorized), Toast.LENGTH_SHORT).show()
        ContextCompat.startActivity(requireContext(), RegisterActivity.getStartIntent(requireContext()),null)
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