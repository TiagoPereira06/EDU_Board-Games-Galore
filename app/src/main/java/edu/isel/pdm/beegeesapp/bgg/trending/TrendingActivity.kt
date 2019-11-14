package edu.isel.pdm.beegeesapp.bgg.trending

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.DetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.games.view.GameViewHolder
import edu.isel.pdm.beegeesapp.bgg.request.RequestInfo
import edu.isel.pdm.beegeesapp.kotlinx.getViewModel
import kotlinx.android.synthetic.main.activity_trending.*

private lateinit var trendingGames: GamesViewModel
private const val GAMES_LIST_KEY = "trending_games_list"
private const val IS_RECONFIGURING_KEY = "is_reconfiguring_flag"
private val requestType = RequestInfo()
private var indexToAskMoreData = 6
private var askedMoreData = false

class TrendingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)

        supportActionBar?.title = getString(R.string.dash_trendingInfo)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        trending_recycler_view.layoutManager = LinearLayoutManager(this)
        trending_recycler_view.setHasFixedSize(true)

        trending_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (askedMoreData) {
                        askedMoreData = false
                        trendingGames.updateModel()
                        changeCursorPosition(recyclerView)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (askedMoreData) return
                val layout = recyclerView.layoutManager as LinearLayoutManager
                if (layout.findLastVisibleItemPosition() == indexToAskMoreData) {
                    askedMoreData = true
                    indexToAskMoreData += requestType.limit
                    trendingGames.updateGames(this@TrendingActivity, requestType)
                }

            }
        })

        // Get view model instance and add its contents to the recycler view
        trendingGames = getViewModel(GAMES_LIST_KEY) {
            savedInstanceState?.getParcelable(GAMES_LIST_KEY) ?: GamesViewModel()
        }
        trending_recycler_view.adapter =
            GameViewHolder.GamesListAdapter(trendingGames) { gameItem: GameInfo ->
                gameItemClicked(gameItem)
            }

        trendingGames.content.observe(this, Observer<List<GameInfo>> {
            trending_recycler_view.swapAdapter(GameViewHolder.GamesListAdapter(trendingGames) { gameItem: GameInfo ->
                gameItemClicked(gameItem)
            }, true)
            pullToRefresh.isRefreshing = false
        })

        // Should we refresh the data?
        if (savedInstanceState == null || !savedInstanceState.containsKey(GAMES_LIST_KEY)) {
            // No saved state? Lets fetch list from the server
            trendingGames.updateGames(
                this,
                requestType
            )
        } else {
            savedInstanceState.remove(IS_RECONFIGURING_KEY)
        }

        // Setup ui event handlers
        pullToRefresh.setOnRefreshListener {
            trendingGames.updateGames(
                this,
                requestType
            )
        }

        pullToRefresh.isRefreshing = true
    }

    private fun changeCursorPosition(recyclerView: RecyclerView) {
        val layout = recyclerView.layoutManager as LinearLayoutManager
        layout.scrollToPosition(requestType.skip - requestType.limit - 1)
    }

    private fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, DetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(intent)
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

