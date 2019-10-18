package edu.isel.pdm.beegeesapp.bgg

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search.*
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.view.GameViewHolder
import edu.isel.pdm.beegeesapp.kotlinx.getViewModel

private const val GAMES_LIST_KEY = "games_list"


class SearchActivity : AppCompatActivity() {


    private lateinit var games: GamesViewModel
    private var optionsSelect = "Name"
    private var lastOption = R.id.search_name
    var searchView: androidx.appcompat.widget.SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        supportActionBar?.title = "Search"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_search)
        list_recycler_view.setHasFixedSize(true)
        list_recycler_view.layoutManager = LinearLayoutManager(this)
        games = getViewModel(GAMES_LIST_KEY) {
            savedInstanceState?.getParcelable(GAMES_LIST_KEY) ?: GamesViewModel()
        }
        list_recycler_view.adapter = GameViewHolder.GamesListAdapter(games) { gameItem : GameInfo -> gameItemClicked(gameItem) }
    }

    private fun gameItemClicked(gameItem : GameInfo) {
        Toast.makeText(this, "Clicked: ${gameItem.name}", Toast.LENGTH_LONG).show()
        val intent = Intent(this,DetailedViewActivity::class.java)
        intent.putExtra("Game Object", gameItem)
        startActivity(intent)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!isChangingConfigurations) {
            outState.putParcelable(GAMES_LIST_KEY, games)
        }
    }


}