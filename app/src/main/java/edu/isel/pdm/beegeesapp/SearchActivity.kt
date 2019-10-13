package edu.isel.pdm.beegeesapp

import android.app.PendingIntent.getActivity
import android.app.SearchManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class SearchActivity : AppCompatActivity() {
    private var optionsSelect = "Name"
    private var lastOption = R.id.search_name
    var searchView: androidx.appcompat.widget.SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        supportActionBar?.title = "Search"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        setContentView(R.layout.activity_search)

        val games = listOf(
            Game(R.drawable.thumb, 95, "Spirit Island", "Greater Than Games", 53.45, 4.97754),
            Game(R.drawable.thumb, 125, "GVgv HUUvwd", "Hbbfih DIUHUh IGGIg", 10.45, 1.97754),
            Game(R.drawable.thumb, 100, "KJBjib IBhgd", "Greater Than Games", 22.45, 2.97754),
            Game(R.drawable.thumb, 15, "SDJHwhd AJdihd", "Greater Than Games", 20.45, 2.97754),
            Game(R.drawable.thumb, 22, "Spirit Island", "Greater Than Games", 41.45, 1.97754),
            Game(R.drawable.thumb, 105, "Spirit Island", "Greater Than Games", 124.45, 0.97754)
        )
        search_recycler_view.layoutManager = LinearLayoutManager(this)
        search_recycler_view.adapter = RecyclerViewAdapter(games)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.top_searchbar, menu)


        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_item)
        searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView

        searchView!!.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView!!.queryHint = "Search by " + optionsSelect

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView!!.clearFocus()
                searchView!!.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(
                    applicationContext,
                    "Search " + query + " " + optionsSelect,
                    Toast.LENGTH_LONG
                ).show()
                return true
            }

            override fun onQueryTextChange(newtext: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val changeOptionAvailable = false

        //item.isChecked = !item.isChecked

        when (item.itemId) {

            R.id.search_author -> {
                /*if (changeOptionAvailable && lastOption != R.id.search_author) optionsSelect =
                    "Author"*/
                optionsSelect="Author"
                searchView?.queryHint = "Search by " + optionsSelect
                println(optionsSelect)

            }
            R.id.search_publisher -> {
                optionsSelect = "Publisher"
                searchView?.queryHint = "Search by " + optionsSelect

            }
            R.id.search_name -> {
                optionsSelect = "Name"
                searchView?.queryHint = "Search by " + optionsSelect

            }
            R.id.home_item -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
/*        val fm = MainActivity().supportFragmentManager
        fm.popBackStack()*/
    }
}