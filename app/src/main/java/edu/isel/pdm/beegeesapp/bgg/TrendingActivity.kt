package edu.isel.pdm.beegeesapp.bgg

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.model.GamesViewModel
import edu.isel.pdm.beegeesapp.bgg.view.GameViewHolder
import edu.isel.pdm.beegeesapp.kotlinx.getViewModel
import kotlinx.android.synthetic.main.activities_fav_trending.*

private lateinit var trendingGames: GamesViewModel
private const val GAMES_LIST_KEY = "trending_games_list"
private val searchType : TYPE = TYPE.Trending

class TrendingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_fav_trending)

        supportActionBar?.title = "What's Trending"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        fav_trending_recycler_view.setHasFixedSize(true)
        fav_trending_recycler_view.layoutManager = LinearLayoutManager(this)
        trendingGames = getViewModel(GAMES_LIST_KEY) {
            savedInstanceState?.getParcelable(GAMES_LIST_KEY) ?: GamesViewModel(searchType)
        }
        fav_trending_recycler_view.adapter = GameViewHolder.GamesListAdapter(trendingGames) { gameItem : GameInfo -> gameItemClicked(gameItem) }

    }

    private fun gameItemClicked(gameItem : GameInfo) {
        Toast.makeText(this, "Clicked: ${gameItem.name}", Toast.LENGTH_LONG).show()
        val intent = Intent(this,DetailedViewActivity::class.java)
        intent.putExtra("Game Object", gameItem)
        startActivity(intent)
    }
}

