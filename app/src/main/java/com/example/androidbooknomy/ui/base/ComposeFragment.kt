package com.example.androidbooknomy.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

abstract class ComposeFragment<State : CoreState, Event : CoreEvent> :
    Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        onBackPressed()
        setContent {
            FragmentContent()
        }
    }

    @Composable
    abstract fun FragmentContent()

    abstract fun onBackPressed()

}