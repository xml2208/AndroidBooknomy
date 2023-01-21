package com.example.androidbooknomy.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

abstract class ComposeFragment<State : CoreState, Event : CoreEvent, Effect : CoreEffect> :
    Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            FragmentContent()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeEffects()
        observeAlbumId()
    }

    private fun observeEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            retrieveViewModel().effect.collect {
                handleEffect(it)
            }
        }
    }
    protected open fun observeAlbumId() {}
    abstract fun retrieveViewModel(): BaseViewModel<State, Event, Effect>

    @Composable
    abstract fun FragmentContent()

    protected open fun handleEffect(effect: Effect) {}

    protected open fun externalObservers() {}

}