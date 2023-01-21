package com.example.androidbooknomy.ui.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidbooknomy.R
import com.example.androidbooknomy.databinding.MainFragmentBinding
import com.example.androidbooknomy.ui.feature.main.main_app.books.BooksScreenFragment
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.EntertainmentFragment
import com.example.androidbooknomy.ui.feature.main.main_app.home.HomeScreenFragment
import com.example.androidbooknomy.ui.feature.main.main_app.library.UserLibraryFragment
import com.example.androidbooknomy.utils.extension.openBottomNavFragment
import kotlin.properties.Delegates

class MainFragment : Fragment() {

    private var _vb: MainFragmentBinding? = null
    private val viewBinding get() = _vb!!
    private val homeScreenFragment = HomeScreenFragment()
    private val bookScreen = BooksScreenFragment()
    private val entertainmentScreen = EntertainmentFragment()
    private val libraryFragment = UserLibraryFragment()
    private val containerId = R.id.main_fragment_container
    private var itemId by Delegates.notNull<Int>()

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
                R.id.ic_home -> openBottomNavFragment(homeScreenFragment, containerId)
                R.id.ic_books -> openBottomNavFragment(bookScreen, containerId)
                R.id.ic_library -> openBottomNavFragment(libraryFragment, containerId)
                R.id.ic_entertainment -> openBottomNavFragment(entertainmentScreen, containerId)
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