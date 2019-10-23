package edu.isel.pdm.beegeesapp.bgg.search

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
import edu.isel.pdm.beegeesapp.bgg.*
import edu.isel.pdm.beegeesapp.bgg.search.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.search.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.search.model.SearchInfo
import edu.isel.pdm.beegeesapp.bgg.search.view.GameViewHolder
import edu.isel.pdm.beegeesapp.kotlinx.getViewModel

private const val GAMES_LIST_KEY = "search_games_list"
private var searchType =
    SearchInfo(Type.Name, null)
private lateinit var searchGames: GamesViewModel
private var lastOption = R.id.search_name
var initSearchWithValue: Boolean = false

class SearchActivity : AppCompatActivity() {
    var searchView: androidx.appcompat.widget.SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        if(intent.hasExtra("SEARCH_KEYWORD")){
            initSearchWithValue = true
            val currentInfo = intent.getParcelableExtra("SEARCH_KEYWORD") as SearchInfo
            searchType.mode=currentInfo.mode
            searchType.keyWord=currentInfo.keyWord
            //TODO: APLICAR SEARCH
        }
        super.onCreate(savedInstanceState)
        //supportActionBar?.hide()
        supportActionBar?.title = "Search"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_search)
        list_recycler_view.setHasFixedSize(true)
        list_recycler_view.layoutManager = LinearLayoutManager(this)



        // Get view model instance and add its contents to the recycler view
        searchGames = getViewModel(GAMES_LIST_KEY) {
            savedInstanceState?.getParcelable(GAMES_LIST_KEY) ?: GamesViewModel()
        }
        list_recycler_view.adapter =
            GameViewHolder.GamesListAdapter(searchGames) { gameItem: GameInfo ->
                gameItemClicked(gameItem)
            }
    }

    private fun gameItemClicked(gameItem : GameInfo) {
        Toast.makeText(this, "Clicked: ${gameItem.name}", Toast.LENGTH_LONG).show()
        val intent = Intent(this, DetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
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
        searchView!!.queryHint = "Search by ${searchType.mode}"
        //TODO:SUBMIT???
        if(initSearchWithValue)searchView!!.setQuery(searchType.keyWord,true)

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView!!.clearFocus()
                searchView!!.setQuery("", false)
                searchItem.collapseActionView()
                searchGames.updateGames(this@SearchActivity,
                    searchType
                )
                /*Toast.makeText(
                    applicationContext,
                    "Search by " + query +  searchType.toString(),
                    Toast.LENGTH_LONG
                ).show()*/
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
                searchType.mode=
                    Type.Author
                searchView?.queryHint = "Search by ${searchType.mode}"

            }
            R.id.search_publisher -> {
                searchType.mode=
                    Type.Publisher
                searchView?.queryHint = "Search by ${searchType.mode}"
            }
            R.id.search_name -> {
                searchType.mode=
                    Type.Name
                searchView?.queryHint = "Search by ${searchType.mode}"

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (!isChangingConfigurations) {
            outState.putParcelable(
                GAMES_LIST_KEY,
                searchGames
            )
        }
    }


}