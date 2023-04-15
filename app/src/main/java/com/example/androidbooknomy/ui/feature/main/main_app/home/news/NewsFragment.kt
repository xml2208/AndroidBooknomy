package com.example.androidbooknomy.ui.feature.main.main_app.home.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.androidbooknomy.cicirone.Screens
import com.example.androidbooknomy.utils.extension.handleBackPressedEvent
import com.github.terrakok.cicerone.Router
import org.koin.android.ext.android.inject

class NewsFragment : Fragment() {

    private val router: Router by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        handleBackPressedEvent { router.navigateTo(Screens.mainFragment()) }
        setContent {
            AllNews()
        }
    }
}

@Composable
fun AllNews() {

}