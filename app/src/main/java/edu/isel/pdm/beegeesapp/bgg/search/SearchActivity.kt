package edu.isel.pdm.beegeesapp.bgg.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.DetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.dialogs.AddToListDialog
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.games.view.GameViewHolder
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.kotlinx.getViewModel
import kotlinx.android.synthetic.main.activity_search.*

private const val GAMES_LIST_KEY = "search_games_list"
private var searchType = RequestInfo(Type.Name, null)
private lateinit var searchGames: GamesViewModel
private var lastItemClicked : MenuItem? = null
var initSearchWithValue: Boolean = false

class SearchActivity : AppCompatActivity() {
    var searchView: androidx.appcompat.widget.SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (intent.hasExtra("SEARCH_KEYWORD")) {
            initSearchWithValue = true
            val currentInfo = intent.getParcelableExtra("SEARCH_KEYWORD") as RequestInfo
            searchType.mode = currentInfo.mode
            searchType.keyWord = currentInfo.keyWord
        }
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.dash_search)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))
        setContentView(R.layout.activity_search)
        search_recycler_view.setHasFixedSize(true)
        search_recycler_view.layoutManager = LinearLayoutManager(this)


        // Get view model instance and add its contents to the recycler view
        searchGames = getViewModel(GAMES_LIST_KEY) {
            savedInstanceState?.getParcelable(GAMES_LIST_KEY) ?: GamesViewModel()
        }
        search_recycler_view.adapter =
            GameViewHolder.GamesListAdapter(searchGames , { gameItem: GameInfo ->
                gameItemClicked(gameItem)},{gameItem: GameInfo -> addToCollectionItemClicked(gameItem)})

        searchGames.content.observe(this, Observer<List<GameInfo>>{
            search_recycler_view.adapter = GameViewHolder.GamesListAdapter(searchGames , { gameItem: GameInfo ->
                gameItemClicked(gameItem)},{gameItem: GameInfo -> addToCollectionItemClicked(gameItem)})
        })
        if(initSearchWithValue){
            searchGames.updateGames(this, searchType)
            supportActionBar?.title = searchType.keyWord
        }
    }
    private fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, DetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(intent)
    }

    private fun addToCollectionItemClicked(gameItem: GameInfo){
        val dialog = AddToListDialog(mutableListOf()) //LISTA VAZIA = MODO CRIAÇÃO
        dialog.show(supportFragmentManager,"New List Dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_searchbar, menu)

        lastItemClicked = if(initSearchWithValue) {
            when (searchType.mode) {
                Type.Artist -> menu?.findItem(R.id.search_artist)
                Type.Publisher -> menu?.findItem(R.id.search_publisher)
                else -> menu?.findItem(R.id.search_name)
            }

        } else menu?.findItem(R.id.search_name)
        lastItemClicked?.isChecked=true

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_item)
        searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView!!.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView!!.queryHint = "Search by ${searchType.mode}"

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView!!.clearFocus()
                searchView!!.setQuery("", false)
                searchType.keyWord=query
                searchItem.collapseActionView()
                searchGames.updateGames(
                    this@SearchActivity,
                    searchType
                )
                supportActionBar?.title = searchType.keyWord
                return true
            }
            override fun onQueryTextChange(newtext: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lastItemClicked?.isChecked=false
        when (item.itemId) {
            R.id.search_artist -> {
                searchType.mode = Type.Artist
                searchView?.queryHint = "Search by ${searchType.mode}"
            }
            R.id.search_publisher -> {
                searchType.mode = Type.Publisher
                searchView?.queryHint = "Search by ${searchType.mode}"
            }
            R.id.search_name -> {
                searchType.mode = Type.Name
                searchView?.queryHint = "Search by ${searchType.mode}"
            }
        }
        lastItemClicked=item
        lastItemClicked!!.isChecked=true
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