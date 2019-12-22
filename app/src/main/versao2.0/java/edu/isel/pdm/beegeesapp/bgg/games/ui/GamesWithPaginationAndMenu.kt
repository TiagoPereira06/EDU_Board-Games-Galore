package edu.isel.pdm.beegeesapp.bgg.games.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.games.model.SearchType

class GamesWithPaginationAndMenu : GamesWithPagination() {

    private var lastItemClicked : MenuItem? = null

    private var searchView : androidx.appcompat.widget.SearchView? = null

    private var initialSearch = false

    override fun initTitle() {
        if (intent.hasExtra("INITIAL_SEARCH")) {
            initialSearch = true
            supportActionBar?.title = viewModel.request.keyWord
        } else {
            supportActionBar?.title = getString(R.string.dash_search)
        }
    }

    override fun initBehavior(savedInstanceState: Bundle?) {
        observeViewModel()
        onSwipeListener()

        if (initialSearch) {
            initialSearch = false
            viewModel.clearSearch()
            updateGames()
        }

    }

    override fun isRequestValid(): Boolean {
        return viewModel.isValidRequest()
    }


    /**
     * Menu Properties
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_searchbar, menu)

        lastItemClicked = if (initialSearch) {
            when (viewModel.request.searchType) {
                SearchType.Artist -> menu?.findItem(R.id.search_artist)
                SearchType.Publisher -> menu?.findItem(R.id.search_publisher)
                else -> menu?.findItem(R.id.search_name)
            }

        } else menu?.findItem(R.id.search_name)

        lastItemClicked?.isChecked = true

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_item)
        searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView!!.setSearchableInfo(manager.getSearchableInfo(componentName))
        //TODO -> PLACE HOLDERS
        searchView!!.queryHint = "Search by ${viewModel.request.searchType}"

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView!!.clearFocus()
                searchView!!.setQuery("", false)
                viewModel.request.keyWord = query
                searchItem.collapseActionView()
                supportActionBar?.title = viewModel.request.keyWord
                viewModel.clear()
                updateGames()
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
                viewModel.request.searchType =
                    SearchType.Artist
            }
            R.id.search_publisher -> {
                viewModel.request.searchType =
                    SearchType.Publisher
            }
            R.id.search_name -> {
                viewModel.request.searchType =
                    SearchType.Name
            }
        }
        //TODO -> PLACE HOLDERS
        searchView?.queryHint = "Search by ${viewModel.request.searchType}"

        lastItemClicked = item
        lastItemClicked!!.isChecked = true
        return super.onOptionsItemSelected(item)
    }
}