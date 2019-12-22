package edu.isel.pdm.beegeesapp.bgg.games.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.games.GamesActivity
import edu.isel.pdm.beegeesapp.bgg.games.view.GamesListAdapter
import kotlinx.android.synthetic.main.recycler_view.*

open class GamesWithPagination : GamesActivity() {

    override fun initTitle() {
        supportActionBar?.title = getString(R.string.dash_trendingInfo)
    }

    override fun initView() {
        super.initView()
        //Pagination
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.checkIfDataIsNeeded(layoutManager.findLastVisibleItemPosition()) {
                    updateGames()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun initBehavior(savedInstanceState: Bundle?) {
        observeViewModel()
        onSwipeListener()

        // No saved state
        if (savedInstanceState == null) {
            viewModel.clearSearch()
            updateGames()
        }

    }

    fun observeViewModel() {
        viewModel.games.observe(this, Observer {
            searchSwipeLayout.isRefreshing = false
            refreshAdapter()
        })
    }

    fun onSwipeListener() {
        // On Refresh listener
        searchSwipeLayout.setOnRefreshListener {
            viewModel.clear()
            (recycler_view.adapter as GamesListAdapter).notifyDataSetChanged()
            updateGames()
        }
    }

    fun updateGames() {
        if (!isRequestValid()) {
            searchSwipeLayout.isRefreshing = false
        } else {
            searchSwipeLayout.isRefreshing = true
            viewModel.getGames {
                //Todo -> Tradução
                Toast.makeText(this, "Check your Internet Connection!", Toast.LENGTH_SHORT)
                    .show()
                searchSwipeLayout.isRefreshing = false
            }
        }
    }

    open fun isRequestValid(): Boolean {
        return true
    }

    private fun refreshAdapter() {
        val position = viewModel.getInsertPosition()
        val size = viewModel.getLiveDataSize()
        val numberOfGames = size - position

        if (numberOfGames == 0) {
            if (position == 0) {
                //TODO - tradução
                Toast.makeText(this, "No games found!", Toast.LENGTH_SHORT)
                    .show()
            } else {
                //TODO - tradução
                Toast.makeText(this, "No more games found!", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            (recycler_view.adapter as GamesListAdapter).notifyItemRangeInserted(
                position,
                numberOfGames
            )
        }
    }

}