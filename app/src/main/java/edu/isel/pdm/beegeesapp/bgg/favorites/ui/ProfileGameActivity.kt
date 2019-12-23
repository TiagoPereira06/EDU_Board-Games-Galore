package edu.isel.pdm.beegeesapp.bgg.favorites.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import edu.isel.pdm.beegeesapp.R
import edu.isel.pdm.beegeesapp.bgg.favorites.model.GameProfile
import edu.isel.pdm.beegeesapp.bgg.games.model.GameInfo
import edu.isel.pdm.beegeesapp.bgg.games.ui.GameDetailedViewActivity
import edu.isel.pdm.beegeesapp.bgg.games.view.GamesListAdapter
import edu.isel.pdm.beegeesapp.bgg.userLists.ui.AddGameToCollection
import kotlinx.android.synthetic.main.recycler_view.*

class ProfileGameActivity : AppCompatActivity() {

    companion object {
        val GAME_PROFILE = "GAME_PROFILE_OBJECT"

        fun createIntent(origin: Context, gameProfile: GameProfile) =
            Intent(origin, ProfileGameActivity::class.java).apply { putExtra(GAME_PROFILE, gameProfile) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorAccent)))

        var profile = intent.getParcelableExtra(GAME_PROFILE) as GameProfile

        supportActionBar?.title = profile.name

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)

        recycler_view.adapter = GamesListAdapter(profile,
            { gameItem : GameInfo -> gameItemClicked(gameItem) },
            { gameItem : GameInfo -> addToCollectionItemClicked(gameItem) })

        searchSwipeLayout.setOnRefreshListener {
            profile = intent.getParcelableExtra(GAME_PROFILE) as GameProfile
            recycler_view.adapter = GamesListAdapter(profile,
                { gameItem : GameInfo -> gameItemClicked(gameItem) },
                { gameItem : GameInfo -> addToCollectionItemClicked(gameItem) })
            searchSwipeLayout.isRefreshing = false
        }
    }

    //TODO -> Repetição de código
    private fun gameItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, GameDetailedViewActivity::class.java)
        intent.putExtra("GAME_OBJECT", gameItem)
        startActivity(intent)
    }

    private fun addToCollectionItemClicked(gameItem: GameInfo) {
        val intent = Intent(this, AddGameToCollection::class.java)
        intent.putExtra("GAME_INFO", gameItem)
        startActivity(intent)
    }
}