package edu.isel.pdm.beegeesapp.bgg.trending

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.DetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.search.model.SearchInfo
import edu.isel.pdm.beegeesapp.bgg.games.view.GameViewHolder
import edu.isel.pdm.beegeesapp.kotlinx.getViewModel
import kotlinx.android.synthetic.main.activity_trending.*

private lateinit var trendingGames: GamesViewModel
private const val GAMES_LIST_KEY = "trending_games_list"
private const val IS_RECONFIGURING_KEY = "is_reconfiguring_flag"
private val searchType = SearchInfo()

class TrendingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending)

        supportActionBar?.title = "What's Trending"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        trending_recycler_view.setHasFixedSize(true)
        trending_recycler_view.layoutManager = LinearLayoutManager(this)

        // Get view model instance and add its contents to the recycler view
        trendingGames = getViewModel(GAMES_LIST_KEY) {
            savedInstanceState?.getParcelable(GAMES_LIST_KEY) ?: GamesViewModel()
        }
        trending_recycler_view.adapter =
            GameViewHolder.GamesListAdapter(trendingGames) { gameItem: GameInfo ->
                gameItemClicked(gameItem)
            }

        trendingGames.content.observe(this, Observer<List<GameInfo>>{
            trending_recycler_view.adapter = GameViewHolder.GamesListAdapter(trendingGames) { gameItem: GameInfo ->
                gameItemClicked(gameItem)
            }
            pullToRefresh.isRefreshing=false

        })


        // Should we refresh the data?
        if (savedInstanceState == null || !savedInstanceState.containsKey(GAMES_LIST_KEY)) {
            // No saved state? Lets fetch list from the server
            trendingGames.updateGames(
                this,
                searchType
            )
        }
        else {
            savedInstanceState.remove(IS_RECONFIGURING_KEY)
        }




        // Setup ui event handlers
        pullToRefresh.setOnRefreshListener {
            trendingGames.updateGames(
                this,
                searchType
            )
        }
    }

    private fun gameItemClicked(gameItem: GameInfo) {
        Toast.makeText(this, "Clicked: ${gameItem.name}", Toast.LENGTH_LONG).show()
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
        }
        else {
            outState.putParcelable(GAMES_LIST_KEY, trendingGames)
        }
    }
}

