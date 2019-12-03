package edu.isel.pdm.beegeesapp.bgg.mainActivities.trending

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.mainActivities.GameDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.GamesRepository
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.chooselisttoadd.ChooseListToAddGameActivity
import edu.isel.pdm.beegeesapp.bgg.auxiliaryActivities.dialogs.ErrorDialog
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.games.view.GameViewHolder
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.bgg.mainActivities.search.Type
import kotlinx.android.synthetic.main.activity_trending.*

class TrendingActivity : AppCompatActivity() {

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
    private val CODE = 15
    private lateinit var lastGameClicked : GameInfo
    private lateinit var repo : GamesRepository
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = (application as BggApplication).repo
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

        // First time calling onCreate
        if (savedInstanceState == null) {
            pullToRefresh.isRefreshing = true
            gamesViewModel.clearSearch()
            gamesViewModel.getGames {
                notifyDataChanged() // notify all items
                pullToRefresh.isRefreshing = false
            }
        }

        // Setup ui event handlers
        pullToRefresh.setOnRefreshListener {
            pullToRefresh.isRefreshing = true
            gamesViewModel.clear()
            gamesViewModel.getGames {
                notifyDataChanged() //notify all items
                pullToRefresh.isRefreshing = false
            }
        }
    }

    private fun updateGames() {
        gamesViewModel.getGames {
            val size = gamesViewModel.getLiveDataSize()
            val position = gamesViewModel.getFirstInsertPosition()
            notifyItemRangeInserted(position, size - position) // notify only inserted items
        }
    }

    private fun getGamesAdapter(): GameViewHolder.GamesListAdapter =
        GameViewHolder.GamesListAdapter(
            gamesViewModel,
            { gameItem: GameInfo -> gameItemClicked(gameItem) },
            { gameItem: GameInfo -> addToCollectionItemClicked(gameItem) })

    private fun notifyDataChanged() {
        (trending_recycler_view.adapter as GameViewHolder.GamesListAdapter).notifyDataSetChanged()
    }

    private fun notifyItemRangeInserted(position: Int, itemsCount: Int) {
        (trending_recycler_view.adapter as GameViewHolder.GamesListAdapter).notifyItemRangeInserted(
            position,
            itemsCount
        )
    }

    private fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, GameDetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(intent)
    }

    private fun addToCollectionItemClicked(gameItem: GameInfo) {
        lastGameClicked=gameItem
        if (gamesViewModel.getCustomUserListFromRepo().isNullOrEmpty()) {
            val dialog = ErrorDialog()
            dialog.retainInstance = true
            dialog.show(supportFragmentManager, "Error Dialog")
        } else {
            val intent = Intent(this, ChooseListToAddGameActivity::class.java)
            intent.putExtra("GAME_INFO", gameItem)
            startActivityForResult(intent,CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //CHAMAR VIEW MODEL?
            val arrayOfNamesToAdd = data!!.getStringArrayExtra("RETURN_ARRAYTOADD")
            val arrayOfNamesToRemove = data.getStringArrayExtra("RETURN_ARRAYTOREMOVE")
            arrayOfNamesToAdd.forEach { listNameToAddGame ->
                repo.addGameToCustomUserList(listNameToAddGame, lastGameClicked)
            }

            arrayOfNamesToRemove.forEach { listNameToRemoveGame ->
                repo.removeGameFromCustomUserList(listNameToRemoveGame, lastGameClicked)
            }
        }
    }

}

