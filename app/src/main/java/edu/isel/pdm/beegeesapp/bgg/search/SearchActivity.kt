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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.GameDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.dialogs.createnewlist.CreateNewListDialog
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.games.view.GameViewHolder
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import kotlinx.android.synthetic.main.activity_search.*

private lateinit var searchType: RequestInfo
private lateinit var searchGames: GamesViewModel
private var lastItemClicked: MenuItem? = null
private var initialSearch = false

class SearchActivity : AppCompatActivity() {
    var searchView: androidx.appcompat.widget.SearchView? = null

    private fun getViewModelFactory(request: RequestInfo) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GamesViewModel(request, application as BggApplication) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        setContentView(R.layout.activity_search)
        search_recycler_view.setHasFixedSize(true)
        search_recycler_view.layoutManager = LinearLayoutManager(this)

        val layoutManager: LinearLayoutManager =
            search_recycler_view.layoutManager as LinearLayoutManager

        if (intent.hasExtra("SEARCH_KEYWORD")) {
            initialSearch = true
            searchType = intent.getParcelableExtra("SEARCH_KEYWORD") as RequestInfo
        } else {
            supportActionBar?.title = getString(R.string.dash_search)
            searchType = RequestInfo(Type.Name, null)
        }

        searchGames = ViewModelProviders
            .of(this, getViewModelFactory(searchType))
            .get(GamesViewModel::class.java)

        search_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                searchGames.checkIfDataIsNeeded(layoutManager.findLastVisibleItemPosition()) {
                    updateGames(false)
                }
            }
        })

        search_recycler_view.adapter = getGamesAdapter()

        searchGames.content.observe(this, Observer {
            searchSwipeLayout.isRefreshing = false
            refreshAdapter()
        })

        if (initialSearch) {
            initialSearch = false
            supportActionBar?.title = searchType.keyWord
            searchGames.clear()
            updateGames(false)
        }

        searchSwipeLayout.setOnRefreshListener {
            searchGames.clear()
            updateGames(true)
        }
    }

    private fun updateGames(queryCheck: Boolean) {
        if (queryCheck && !searchGames.isValidRequest()) {
            searchSwipeLayout.isRefreshing = false
        } else {
            searchSwipeLayout.isRefreshing = true
            searchGames.getGames {
                refreshAdapter()
            }
        }
    }

    private fun refreshAdapter() {
        val position = searchGames.getInsertPosition()
        val size = searchGames.getLiveDataSize()
        if (position == 0) { // position = 0 <=> LiveData is empty/cleared, no old items present
            // notify all items ->
            (search_recycler_view.adapter as GameViewHolder.GamesListAdapter)
                .notifyDataSetChanged()
        } else {
            // notify only inserted items
            (search_recycler_view.adapter as GameViewHolder.GamesListAdapter)
                .notifyItemRangeInserted(position, size - position)
        }
    }

    private fun getGamesAdapter(): GameViewHolder.GamesListAdapter =
        GameViewHolder.GamesListAdapter(
            searchGames,
            { gameItem: GameInfo -> gameItemClicked(gameItem) },
            { gameItem: GameInfo -> addToCollectionItemClicked(gameItem) })

    private fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, GameDetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(intent)
    }

    private fun addToCollectionItemClicked(gameItem: GameInfo) {
        val dialog =
            CreateNewListDialog() //LISTA VAZIA = MODO CRIAÇÃO
        dialog.show(supportFragmentManager, "New List Dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_searchbar, menu)

        lastItemClicked = if (initialSearch) {
            when (searchType.mode) {
                Type.Artist -> menu?.findItem(R.id.search_artist)
                Type.Publisher -> menu?.findItem(R.id.search_publisher)
                else -> menu?.findItem(R.id.search_name)
            }

        } else menu?.findItem(R.id.search_name)
        lastItemClicked?.isChecked = true

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
                searchType.keyWord = query
                searchItem.collapseActionView()
                supportActionBar?.title = searchType.keyWord
                searchGames.clear()
                updateGames(true)
                return true
            }

            override fun onQueryTextChange(newtext: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lastItemClicked?.isChecked = false
        when (item.itemId) {
            R.id.search_artist -> {
                searchType.mode = Type.Artist
            }
            R.id.search_publisher -> {
                searchType.mode = Type.Publisher
            }
            R.id.search_name -> {
                searchType.mode = Type.Name
            }
        }
        searchView?.queryHint = "Search by ${searchType.mode}"
        lastItemClicked = item
        lastItemClicked!!.isChecked = true
        return super.onOptionsItemSelected(item)
    }

}