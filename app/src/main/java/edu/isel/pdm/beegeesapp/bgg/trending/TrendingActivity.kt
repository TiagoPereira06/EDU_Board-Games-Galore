package edu.isel.pdm.beegeesapp.bgg.trending

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.BggApplication
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.GameDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.chooselisttoadd.ChooseListToAddGameActivity
import edu.isel.pdm.beegeesapp.bgg.dialogs.ErrorDialog
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.games.view.GameViewHolder
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.bgg.userlists.UserListsActivity
import edu.isel.pdm.beegeesapp.kotlinx.getViewModel
import kotlinx.android.synthetic.main.activity_trending.*

private const val GAMES_LIST_KEY = "trending_games_list"
private const val IS_RECONFIGURING_KEY = "is_reconfiguring_flag"
private const val INITIAL_INDEX = 5
private var askedMoreData: Boolean = false
private var INDEX_TO_ASK_MORE_DATA = INITIAL_INDEX

private val request: RequestInfo = RequestInfo()
private lateinit var trendingGames: GamesViewModel

private var lists = UserListsActivity.listContainer

@Suppress("DEPRECATION")
class TrendingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)

        supportActionBar?.title = getString(R.string.dash_trendingInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        trending_recycler_view.layoutManager = LinearLayoutManager(this)
        trending_recycler_view.setHasFixedSize(true)

        trending_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layout = recyclerView.layoutManager as LinearLayoutManager
                if (!askedMoreData && layout.findLastVisibleItemPosition() >= INDEX_TO_ASK_MORE_DATA) {
                    askedMoreData = true
                    INDEX_TO_ASK_MORE_DATA += request.limit
                    updateGames(application as BggApplication, request)
                }
            }
        })

        // Get view model instance and add its contents to the recycler view
        trendingGames = getViewModel(GAMES_LIST_KEY) {
            savedInstanceState?.getParcelable(GAMES_LIST_KEY) ?: GamesViewModel()
        }

        trending_recycler_view.adapter = GameViewHolder.GamesListAdapter(
            trendingGames,
            { gameItem: GameInfo -> gameItemClicked(gameItem) },
            { gameItem: GameInfo -> addToCollectionItemClicked(gameItem)})

        trendingGames.content.observe(this, Observer {
            trending_recycler_view.swapAdapter(GameViewHolder.GamesListAdapter(trendingGames,
                { gameItem: GameInfo -> gameItemClicked(gameItem) },
                { gameItem: GameInfo -> addToCollectionItemClicked(gameItem) }), true
            )
            pullToRefresh.isRefreshing = false
        })

        // Should we refresh the data?
        if (savedInstanceState == null) {
            // No saved state? Lets fetch list from the server
            clearRequest()
            updateGames(
                application as BggApplication,
                request
            )
        } else {
            savedInstanceState.remove(IS_RECONFIGURING_KEY)
        }


        // Setup ui event handlers
        pullToRefresh.setOnRefreshListener {
            clearRequest()
            clearLiveData()
            updateGames(
                application as BggApplication,
                request
            )
            pullToRefresh.isRefreshing = false
        }
        pullToRefresh.isRefreshing = true
    }

    private fun updateGames(app: BggApplication, request: RequestInfo) {
        trendingGames.getGames(app, request) {
            trending_recycler_view.adapter?.notifyItemRangeInserted(
                request.skip - request.limit,
                it.value!!.size
            )
        }
        askedMoreData = false
    }

    private fun clearRequest() {
        INDEX_TO_ASK_MORE_DATA = INITIAL_INDEX
        request.clear()
    }

    private fun clearLiveData() {
        trendingGames.getLiveData().value?.clear()
        (trending_recycler_view.adapter as GameViewHolder.GamesListAdapter).notifyDataSetChanged()
    }

    private fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, GameDetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(intent)
    }

    private fun addToCollectionItemClicked(gameItem: GameInfo) {
        if (lists.userLists.isNullOrEmpty()) {
            val dialog = ErrorDialog()
            dialog.retainInstance = true
            dialog.show(supportFragmentManager, "Error Dialog")
        } else {
            val intent = Intent(this, ChooseListToAddGameActivity::class.java)
            intent.putExtra("GAME_INFO", gameItem)
            startActivity(intent)
            /* val dialog = ChooseListToAddGameDialog(gameItem)
        dialog.retainInstance = true
        dialog.show(supportFragmentManager,"Choose List Dialog")*/
        }
    }

    /**
     * Callback method that handles view state preservation
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isChangingConfigurations) {
            outState.putBoolean(IS_RECONFIGURING_KEY, true)
        } else {
            outState.putParcelable(GAMES_LIST_KEY, trendingGames)
        }
    }


}

