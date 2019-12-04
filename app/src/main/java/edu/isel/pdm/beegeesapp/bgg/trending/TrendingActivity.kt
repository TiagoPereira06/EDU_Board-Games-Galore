package edu.isel.pdm.beegeesapp.bgg.trending

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import edu.isel.pdm.beegeesapp.bgg.chooselisttoadd.ChooseListToAddGameActivity
import edu.isel.pdm.beegeesapp.bgg.dialogs.ErrorDialog
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.games.view.GameViewHolder
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.bgg.search.Type
import kotlinx.android.synthetic.main.activity_trending.*

class TrendingActivity : AppCompatActivity() {

    //todo repetição
    private fun getViewModelFactory(request: RequestInfo) = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GamesViewModel(request, application as BggApplication) as T
        }
    }

    /**
     * View Model instance
     */
    private lateinit var gamesViewModel: GamesViewModel

    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)

        supportActionBar?.title = getString(R.string.dash_trendingInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        trending_recycler_view.layoutManager = LinearLayoutManager(this)
        trending_recycler_view.setHasFixedSize(true)

        layoutManager = trending_recycler_view.layoutManager as LinearLayoutManager

        /**
         * On scroll listener
         */
        trending_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                gamesViewModel.checkIfDataIsNeeded(layoutManager.findLastVisibleItemPosition()) {
                    updateGames()
                }
            }
        })

        gamesViewModel = ViewModelProviders
            .of(this, getViewModelFactory(RequestInfo(Type.Trending)))
            .get(GamesViewModel::class.java)

        trending_recycler_view.adapter = getGamesAdapter()

        gamesViewModel.content.observe(this, Observer {
            pullToRefresh.isRefreshing = false
            refreshAdapter()
        })

        // First time calling onCreate
        if (savedInstanceState == null) {
            gamesViewModel.clear()
            updateGames()
        }

        // On Refresh listener
        pullToRefresh.setOnRefreshListener {
            gamesViewModel.clear()
            updateGames()
        }
    }

    private fun updateGames() {
        pullToRefresh.isRefreshing = true
        gamesViewModel.getGames {
            refreshAdapter()
        }
    }

    private fun refreshAdapter() {
        val position = gamesViewModel.getInsertPosition()
        val size = gamesViewModel.getLiveDataSize()
        if (position == 0) { // position = 0 <=> LiveData is empty/cleared, no old items present
            // notify all items ->
            (trending_recycler_view.adapter as GameViewHolder.GamesListAdapter)
                .notifyDataSetChanged()
        } else {
            // notify only inserted items
            (trending_recycler_view.adapter as GameViewHolder.GamesListAdapter)
                .notifyItemRangeInserted(position, size - position)
        }
    }

    private fun getGamesAdapter(): GameViewHolder.GamesListAdapter =
        GameViewHolder.GamesListAdapter(
            gamesViewModel,
            { gameItem: GameInfo -> gameItemClicked(gameItem) },
            { gameItem: GameInfo -> addToCollectionItemClicked(gameItem) })

    private fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, GameDetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(intent)
    }

    //TODO - CASO EMPTY..
    private fun addToCollectionItemClicked(gameItem: GameInfo) {
        if (gamesViewModel.getCustomUserListFromRepo().isNullOrEmpty()) {
            val dialog = ErrorDialog()
            dialog.retainInstance = true
            dialog.show(supportFragmentManager, "Error Dialog")
        } else {
            val intent = Intent(this, ChooseListToAddGameActivity::class.java)
            intent.putExtra("GAME_INFO", gameItem)
            startActivity(intent)
        }
    }

}

