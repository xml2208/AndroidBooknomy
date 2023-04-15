package com.example.androidbooknomy.cicirone

import androidx.fragment.app.Fragment
import com.example.androidbooknomy.R
import com.example.androidbooknomy.model.FilmModel
import com.example.androidbooknomy.model.music.MusicAlbumItem
import com.example.androidbooknomy.ui.feature.login.RegisterActivity
import com.example.androidbooknomy.ui.feature.main.MainActivity
import com.example.androidbooknomy.ui.feature.main.MainFragment
import com.example.androidbooknomy.ui.feature.main.main_app.books.BooksScreenFragment
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.EntertainmentFragment
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.film.AboutMovieFragment
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.film.PlayMovie
import com.example.androidbooknomy.ui.feature.main.main_app.entertainment.music.music_list.MusicListFragment
import com.example.androidbooknomy.ui.feature.main.main_app.home.HomeScreenFragment
import com.example.androidbooknomy.ui.feature.main.main_app.home.news.NewsFragment
import com.example.androidbooknomy.ui.feature.main.main_app.library.UserLibraryFragment
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun registerActivity() = ActivityScreen("register_activity") { context ->
        RegisterActivity.getStartIntent(context)
    }

    fun mainActivity() = ActivityScreen("main_activty") { context ->
        MainActivity.getStartIntent(context)
    }

    fun mainFragment() = FragmentScreen("main_fr") {
        MainFragment()
    }

    fun homeFragment() = FragmentScreen("home_fr") {
        HomeScreenFragment()
    }

    fun booksFragment() = FragmentScreen {
        BooksScreenFragment()
    }

    fun userLibraryFragment() = FragmentScreen("userLibrary_fr") {
        UserLibraryFragment()
    }

    fun entertainmentFragment() = FragmentScreen("entertainment_fr") {
        EntertainmentFragment()
    }

    fun newsFragment() = FragmentScreen("news_fr") {
        NewsFragment()
    }

    fun mainFragmentWithBooksSelected() = FragmentScreen("main_fr") {
        MainFragment.newInstance(R.id.ic_books)
    }

    fun mainFragmentWithEntertainmentSelected() = FragmentScreen("main_fr") {
        MainFragment.newInstance(R.id.ic_entertainment)
    }

    fun paymentFragment(fragment: Fragment) = FragmentScreen("payment_screen") {
        fragment
    }

    fun aboutMovieFragment(filmModel: FilmModel) = FragmentScreen("about_movie") {
        AboutMovieFragment.newInstance(filmModel)
    }

    fun musicListFragment(albumItem: MusicAlbumItem) = FragmentScreen("music_list") {
        MusicListFragment.newInstance(albumItem.id)
    }

    fun playMovieFragment(filmModel: FilmModel) = FragmentScreen("music_list") {
        PlayMovie.newInstance(filmModel.films.filmUrl)
    }



}