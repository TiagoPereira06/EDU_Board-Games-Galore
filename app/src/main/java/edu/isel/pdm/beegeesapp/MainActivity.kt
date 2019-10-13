package edu.isel.pdm.beegeesapp

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Favourites"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        replaceFragment(FavouritesFragment())

        val navigationView = findViewById<View>(R.id.navBar) as BottomNavigationView
        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.trending -> {
                    //item.setIcon(R.drawable.trending_full)
                    //startActivity(Intent(this, DetailedViewActivity::class.java))
                    replaceFragment(PopularFragment())
                }
                R.id.search -> {
                    //item.setIcon(R.drawable.search_full)
                    startActivity(Intent(this, SearchActivity::class.java))
                    //replaceFragment(SearchFragment())

                }
                R.id.about ->
                    //Toast.makeText(application, "Search is Clicked", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(this, AboutActivity::class.java))
                    replaceFragment(AboutFragment())

                R.id.favourites ->
                    //Toast.makeText(application, "Search is Clicked", Toast.LENGTH_SHORT).show()
                    //startActivity(Intent(this, AboutActivity::class.java))
                    replaceFragment(FavouritesFragment())
            }
            true
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

}


