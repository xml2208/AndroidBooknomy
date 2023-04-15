package com.example.androidbooknomy.ui.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidbooknomy.R
import com.example.androidbooknomy.analytics.AnalyticsUseCase
import com.example.androidbooknomy.databinding.MainFragmentBinding
import com.example.androidbooknomy.ui.feature.main.main_app.books.BooksScreenFragment
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.EntertainmentFragment
import com.example.androidbooknomy.ui.feature.main.main_app.home.HomeScreenFragment
import com.example.androidbooknomy.ui.feature.main.main_app.library.UserLibraryFragment
import com.example.androidbooknomy.utils.extension.openBottomNavFragment
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates

class MainFragment : Fragment() {

    private var _vb: MainFragmentBinding? = null
    private val viewBinding get() = _vb!!
    private var itemId by Delegates.notNull<Int>()
    private val analytics by inject<AnalyticsUseCase>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = MainFragmentBinding.inflate(layoutInflater)

        itemId = arguments?.getInt("booksId") ?: R.id.ic_home
        return viewBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.appBottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    openBottomNavFragment(containerId = R.id.container, analytics = analytics, fragment = HomeScreenFragment())
                }
                R.id.ic_books -> {
                    openBottomNavFragment(containerId = R.id.container, analytics = analytics, fragment = BooksScreenFragment())
//                    router.navigateTo(Screens.booksFragment())
                }
                R.id.ic_library -> {
                    openBottomNavFragment(containerId = R.id.container, analytics = analytics, fragment = UserLibraryFragment())
//                    router.navigateTo(Screens.userLibraryFragment())
                }
                R.id.ic_entertainment -> {
                    openBottomNavFragment(containerId = R.id.container, analytics = analytics, fragment = EntertainmentFragment())
//                    router.navigateTo(Screens.entertainmentFragment())
                }
            }
            return@setOnItemSelectedListener true
        }
        viewBinding.appBottomBar.selectedItemId = itemId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _vb = null
    }

    companion object {
        fun newInstance(itemId: Int): MainFragment {
            val args = Bundle()
            args.putInt("booksId", itemId)

            return MainFragment().apply {
                arguments = args
            }
        }
    }
}