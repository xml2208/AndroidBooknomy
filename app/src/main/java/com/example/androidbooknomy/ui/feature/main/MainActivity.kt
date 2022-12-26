package com.example.androidbooknomy.ui.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.androidbooknomy.R
import com.example.androidbooknomy.databinding.ActivityMainBinding
import com.example.androidbooknomy.ui.feature.main.main_app.books.BooksScreenFragment
import com.example.androidbooknomy.ui.feature.main.main_app.home.HomeScreenFragment

class MainActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityMainBinding::bind)

    private val homeScreenFragment = HomeScreenFragment()
    private val bookScreen = BooksScreenFragment()
//    private val homeScreen =  ()
//    private val homeScreen =  ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        setCurrentFragment(homeScreenFragment)
        viewBinding.appBottomBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> setCurrentFragment(homeScreenFragment)
                R.id.ic_books -> setCurrentFragment(bookScreen)
//                R.id.ic_books -> Toast.makeText(applicationContext, "pressed", Toast.LENGTH_SHORT).show()
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commitNow()

    companion object {
        fun getStartIntent(context: Context): Intent =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
    }
}