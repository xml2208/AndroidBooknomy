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
    private val homeScreenFragment = HomeScreenFragment()
    private val bookScreenFragment = BooksScreenFragment()
    private val entertainmentScreenFragment = EntertainmentFragment()
    private val libraryFragment = UserLibraryFragment()
    private var activeFragment: Fragment = HomeScreenFragment()
    private val containerId = R.id.main_fragment_container
    private var itemId by Delegates.notNull<Int>()
    private val analytics by inject<AnalyticsUseCase>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _vb = MainFragmentBinding.inflate(layoutInflater)

        itemId = arguments?.getInt("booksId") ?: R.id.ic_home

//        childFragmentManager.beginTransaction()
//            .add(containerId, bookScreenFragment).hide(bookScreenFragment)
//            .add(containerId, libraryFragment).hide(libraryFragment)
//            .add(containerId, entertainmentScreenFragment).hide(entertainmentScreenFragment)
//            .add(containerId, homeScreenFragment)
//            .commit()
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.appBottomBar.setOnItemSelectedListener {
            when (it.itemId) {
//                R.id.ic_home -> {
//                    childFragmentManager.beginTransaction().hide(activeFragment).show(homeScreenFragment).commit()
//                    activeFragment = homeScreenFragment
//                }
//                R.id.ic_books -> {
//                    childFragmentManager.beginTransaction().hide(activeFragment).show(bookScreenFragment).commit()
//                    activeFragment = bookScreenFragment
//                }
//                R.id.ic_library -> {
//                    childFragmentManager.beginTransaction().hide(activeFragment).show(libraryFragment).commit()
//                    activeFragment = libraryFragment
//                }
//                R.id.ic_entertainment -> {
//                    childFragmentManager.beginTransaction().hide(activeFragment).show(entertainmentScreenFragment).commit()
//                    activeFragment = entertainmentScreenFragment
//                }
                R.id.ic_home -> openBottomNavFragment(analytics,homeScreenFragment, containerId)
                R.id.ic_books -> openBottomNavFragment(analytics,bookScreenFragment, containerId)
                R.id.ic_library -> openBottomNavFragment(analytics,libraryFragment, containerId)
                R.id.ic_entertainment -> openBottomNavFragment(analytics,entertainmentScreenFragment, containerId)
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