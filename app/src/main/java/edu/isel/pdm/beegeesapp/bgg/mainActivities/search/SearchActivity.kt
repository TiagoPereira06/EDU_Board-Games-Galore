package edu.isel.pdm.beegeesapp.bgg.mainActivities.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.mainActivities.GameDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.createnewlist.CreateNewListDialog
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.games.view.GameViewHolder
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import kotlinx.android.synthetic.main.activity_search.*

private lateinit var searchType: RequestInfo
private lateinit var searchGames: GamesViewModel
private var lastItemClicked : MenuItem? = null
private var initSearchWithValue = false

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

        initSearchWithValue = false

        setContentView(R.layout.activity_search)
        search_recycler_view.setHasFixedSize(true)
        search_recycler_view.layoutManager = LinearLayoutManager(this)

        val layoutManager: LinearLayoutManager =
            search_recycler_view.layoutManager as LinearLayoutManager

        if (intent.hasExtra("SEARCH_KEYWORD")) {
            initSearchWithValue = true
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
                    search()
                }
            }
        })

        search_recycler_view.adapter = getGamesAdapter()

        if(initSearchWithValue){
            supportActionBar?.title = searchType.keyWord
            searchSwipeLayout.isRefreshing = true
            searchGames.clear()
            searchGames.getGames {
                notifyDataChanged()
                searchSwipeLayout.isRefreshing = false
            }
        }

        searchSwipeLayout.setOnRefreshListener {
            searchSwipeLayout.isRefreshing = true
            searchGames.clear()
            searchGames.getGames {
                notifyDataChanged()
                searchSwipeLayout.isRefreshing = false
            }
        }

    }

    private fun search() {
        searchGames.getGames {
            val size = searchGames.getLiveDataSize()
            val position = searchGames.getFirstInsertPosition()
            notifyItemRangeInserted(position, size - position) // notify only inserted items
        }
    }


    private fun notifyDataChanged() {
        (search_recycler_view.adapter as GameViewHolder.GamesListAdapter).notifyDataSetChanged()
    }

    private fun notifyItemRangeInserted(position: Int, itemsCount: Int) {
        (search_recycler_view.adapter as GameViewHolder.GamesListAdapter).notifyItemRangeInserted(
            position,
            itemsCount
        )
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

    private fun addToCollectionItemClicked(gameItem: GameInfo){
        val dialog =
            CreateNewListDialog() //LISTA VAZIA = MODO CRIAÇÃO
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
                //search()
                searchSwipeLayout.isRefreshing = true
                searchGames.clear()
                searchGames.getGames {
                    notifyDataChanged()
                    searchSwipeLayout.isRefreshing = false
                }
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